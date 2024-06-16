import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchFerienwohnungen, addFerienwohnung as apiAddFerienwohnung } from '../../api/api';
import { Ferienwohnung } from '../../types';

interface FerienwohnungContextType {
    ferienwohnungen: Ferienwohnung[];
    ferienwohnungenLoading: boolean;
    ferienwohnungenError: string | null;
    addFerienwohnung: (newFerienwohnung: Ferienwohnung) => Promise<void>;
    refreshFerienwohnungen: () => Promise<void>;
}

const FerienwohnungContext = createContext<FerienwohnungContextType | undefined>(undefined);

export const useFerienwohnungContext = () => {
    const context = useContext(FerienwohnungContext);
    if (!context) {
        throw new Error('useFerienwohnungContext must be used within a FerienwohnungProvider');
    }
    return context;
};

export const FerienwohnungProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [ferienwohnungen, setFerienwohnungen] = useState<Ferienwohnung[]>([]);
    const [ferienwohnungenLoading, setFerienwohnungenLoading] = useState(false);
    const [ferienwohnungenError, setFerienwohnungenError] = useState<string | null>(null);

    const refreshFerienwohnungen = async (): Promise<void> => {
        try {
            setFerienwohnungenLoading(true);
            const updatedFerienwohnungen = await fetchFerienwohnungen();
            setFerienwohnungen(updatedFerienwohnungen);
            setFerienwohnungenLoading(false);
        } catch (error) {
            setFerienwohnungenError('Failed to refresh Ferienwohnungen');
            setFerienwohnungenLoading(false);
        }
    };

    const addFerienwohnung = async (newFerienwohnung: Ferienwohnung): Promise<void> => {
        try {
            await apiAddFerienwohnung(newFerienwohnung);
            await refreshFerienwohnungen();
        } catch (error) {
            throw new Error(`Failed to add Ferienwohnung: ${error}`);
        }
    };

    return (
        <FerienwohnungContext.Provider value={{ ferienwohnungen, ferienwohnungenLoading, ferienwohnungenError, addFerienwohnung, refreshFerienwohnungen }}>
            {children}
        </FerienwohnungContext.Provider>
    );
};
