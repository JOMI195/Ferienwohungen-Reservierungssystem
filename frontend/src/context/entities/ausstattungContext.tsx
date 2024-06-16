import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchAusstattungen, addAusstattung as apiAddAusstattung } from '../../api/api';
import { Ausstattung } from '../../types';

interface AusstattungContextType {
    ausstattungen: Ausstattung[];
    ausstattungenLoading: boolean;
    ausstattungenError: string | null;
    addAusstattung: (newAusstattung: Ausstattung) => Promise<void>;
    refreshAusstattungen: () => Promise<void>;
}

const AusstattungContext = createContext<AusstattungContextType | undefined>(undefined);

export const useAusstattungContext = () => {
    const context = useContext(AusstattungContext);
    if (!context) {
        throw new Error('useAusstattungContext must be used within a AusstattungProvider');
    }
    return context;
};

export const AusstattungProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [ausstattungen, setAusstattungen] = useState<Ausstattung[]>([]);
    const [ausstattungenLoading, setAusstattungenLoading] = useState(false);
    const [ausstattungenError, setAusstattungenError] = useState<string | null>(null);

    const refreshAusstattungen = async (): Promise<void> => {
        try {
            setAusstattungenLoading(true);
            const updatedAusstattungen = await fetchAusstattungen();
            setAusstattungen(updatedAusstattungen);
            setAusstattungenLoading(false);
        } catch (error) {
            setAusstattungenError('Failed to refresh Ausstattungen');
            setAusstattungenLoading(false);
        }
    };

    const addAusstattung = async (newAusstattung: Ausstattung): Promise<void> => {
        try {
            await apiAddAusstattung(newAusstattung);
            await refreshAusstattungen();
        } catch (error) {
            throw new Error(`Failed to add Ausstattung: ${error}`);
        }
    };

    return (
        <AusstattungContext.Provider value={{ ausstattungen, ausstattungenLoading, ausstattungenError, addAusstattung, refreshAusstattungen }}>
            {children}
        </AusstattungContext.Provider>
    );
};
