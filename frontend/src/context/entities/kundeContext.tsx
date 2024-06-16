import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchKunden, addKunde as apiAddKunde } from '../../api/api';
import { Kunde } from '../../types';

interface KundeContextType {
    kunden: Kunde[];
    kundenLoading: boolean;
    kundenError: string | null;
    addKunde: (newKunde: Kunde) => Promise<void>;
    refreshKunden: () => Promise<void>;
}

const KundeContext = createContext<KundeContextType | undefined>(undefined);

export const useKundeContext = () => {
    const context = useContext(KundeContext);
    if (!context) {
        throw new Error('useKundeContext must be used within a KundeProvider');
    }
    return context;
};

export const KundeProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [kunden, setKunden] = useState<Kunde[]>([]);
    const [kundenLoading, setKundenLoading] = useState(false);
    const [kundenError, setKundenError] = useState<string | null>(null);

    const refreshKunden = async (): Promise<void> => {
        try {
            setKundenLoading(true);
            const updatedKunden = await fetchKunden();
            setKunden(updatedKunden);
            setKundenLoading(false);
        } catch (error) {
            setKundenError('Failed to refresh Kunden');
            setKundenLoading(false);
        }
    };

    const addKunde = async (newKunde: Kunde): Promise<void> => {
        try {
            await apiAddKunde(newKunde);
            await refreshKunden();
        } catch (error) {
            throw new Error(`Failed to add Kunde: ${error}`);
        }
    };

    return (
        <KundeContext.Provider value={{ kunden, kundenLoading, kundenError, addKunde, refreshKunden }}>
            {children}
        </KundeContext.Provider>
    );
};
