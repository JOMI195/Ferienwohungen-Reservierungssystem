import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchBuchungen, addBuchung as apiAddBuchung } from '../../api/api'; // Add appropriate API methods
import { Buchung } from '../../types';

interface BuchungContextType {
    buchungen: Buchung[];
    buchungenLoading: boolean;
    buchungenError: string | null;
    addBuchung: (newBuchung: Buchung) => Promise<void>;
    refreshBuchungen: () => Promise<void>;
}

const BuchungContext = createContext<BuchungContextType | undefined>(undefined);

export const useBuchungContext = () => {
    const context = useContext(BuchungContext);
    if (!context) {
        throw new Error('useBuchungContext must be used within a BuchungProvider');
    }
    return context;
};

export const BuchungProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [buchungen, setBuchungen] = useState<Buchung[]>([]);
    const [buchungenLoading, setBuchungenLoading] = useState(false);
    const [buchungenError, setBuchungenError] = useState<string | null>(null);

    const refreshBuchungen = async (): Promise<void> => {
        try {
            setBuchungenLoading(true);
            const updatedBuchungen = await fetchBuchungen();
            setBuchungen(updatedBuchungen);
            setBuchungenLoading(false);
        } catch (error) {
            setBuchungenError('Failed to refresh Buchungen');
            setBuchungenLoading(false);
        }
    };

    const addBuchung = async (newBuchung: Buchung): Promise<void> => {
        try {
            await apiAddBuchung(newBuchung);
            await refreshBuchungen();
        } catch (error) {
            throw new Error(`Failed to add Buchung: ${error}`);
        }
    };

    return (
        <BuchungContext.Provider value={{
            buchungen,
            buchungenLoading,
            buchungenError,
            addBuchung,
            refreshBuchungen,
        }}>
            {children}
        </BuchungContext.Provider>
    );
};
