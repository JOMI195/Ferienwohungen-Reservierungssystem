import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchLiegtInDerNaeheVon, addLiegtInDerNaeheVon as apiAddLiegtInDerNaeheVon } from '../../api/api';
import { LiegtInDerNaeheVon } from '../../types';

interface LiegtInDerNaeheVonContextType {
    liegtInDerNaeheVon: LiegtInDerNaeheVon[];
    liegtInDerNaeheVonLoading: boolean;
    liegtInDerNaeheVonError: string | null;
    addLiegtInDerNaeheVon: (newLiegtInDerNaeheVon: LiegtInDerNaeheVon) => Promise<void>;
    refreshLiegtInDerNaeheVon: () => Promise<void>;
}

const LiegtInDerNaeheVonContext = createContext<LiegtInDerNaeheVonContextType | undefined>(undefined);

export const useLiegtInDerNaeheVonContext = () => {
    const context = useContext(LiegtInDerNaeheVonContext);
    if (!context) {
        throw new Error('useLiegtInDerNaeheVonContext must be used within a LiegtInDerNaeheVonProvider');
    }
    return context;
};

export const LiegtInDerNaeheVonProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [liegtInDerNaeheVon, setLiegtInDerNaeheVon] = useState<LiegtInDerNaeheVon[]>([]);
    const [liegtInDerNaeheVonLoading, setLiegtInDerNaeheVonLoading] = useState(false);
    const [liegtInDerNaeheVonError, setLiegtInDerNaeheVonError] = useState<string | null>(null);

    const refreshLiegtInDerNaeheVon = async (): Promise<void> => {
        try {
            setLiegtInDerNaeheVonLoading(true);
            const updatedLiegtInDerNaeheVon = await fetchLiegtInDerNaeheVon();
            setLiegtInDerNaeheVon(updatedLiegtInDerNaeheVon);
            setLiegtInDerNaeheVonLoading(false);
        } catch (error) {
            setLiegtInDerNaeheVonError('Failed to refresh LiegtInDerNaeheVon');
            setLiegtInDerNaeheVonLoading(false);
        }
    };

    const addLiegtInDerNaeheVon = async (newLiegtInDerNaeheVon: LiegtInDerNaeheVon): Promise<void> => {
        try {
            await apiAddLiegtInDerNaeheVon(newLiegtInDerNaeheVon);
            await refreshLiegtInDerNaeheVon();
        } catch (error) {
            throw new Error(`Failed to add LiegtInDerNaeheVon: ${error}`);
        }
    };

    return (
        <LiegtInDerNaeheVonContext.Provider value={{ liegtInDerNaeheVon, liegtInDerNaeheVonLoading, liegtInDerNaeheVonError, addLiegtInDerNaeheVon, refreshLiegtInDerNaeheVon }}>
            {children}
        </LiegtInDerNaeheVonContext.Provider>
    );
};
