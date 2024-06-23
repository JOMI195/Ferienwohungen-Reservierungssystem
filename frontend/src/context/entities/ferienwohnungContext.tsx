import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchFerienwohnungen, addFerienwohnung as apiAddFerienwohnung, fetchFilteredFerienwohnungen } from '../../api/api';
import { Ferienwohnung } from '../../types';

interface FerienwohnungContextType {
    ferienwohnungen: Ferienwohnung[];
    ferienwohnungenLoading: boolean;
    ferienwohnungenError: string | null;
    filteredFerienwohnungen: Ferienwohnung[];
    filteredFerienwohnungenLoading: boolean;
    filteredFerienwohnungenError: string | null;
    addFerienwohnung: (newFerienwohnung: Ferienwohnung) => Promise<void>;
    refreshFerienwohnungen: () => Promise<void>;
    filterFerienwohnungen: (landname?: string, ausstattung?: string, startdatum?: string, enddatum?: string) => Promise<void>;
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

    const [filteredFerienwohnungen, setFilteredFerienwohnungen] = useState<Ferienwohnung[]>([]);
    const [filteredFerienwohnungenLoading, setFilteredFerienwohnungenLoading] = useState(false);
    const [filteredFerienwohnungenError, setFilteredFerienwohnungenError] = useState<string | null>(null);

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

    const filterFerienwohnungen = async (
        landname?: string,
        ausstattung?: string,
        startdatum?: string,
        enddatum?: string
    ): Promise<void> => {
        try {
            setFilteredFerienwohnungenLoading(true);
            const fetchedFerienwohnungen = await fetchFerienwohnungen();
            const filteredData = await fetchFilteredFerienwohnungen(landname, ausstattung, startdatum, enddatum);
            console.log(filteredData)
            console.log(fetchedFerienwohnungen)
            const mappedData = filteredData.map(filteredFw => {
                return fetchedFerienwohnungen.find(fw => fw.ferienwohnungs_id === filteredFw.ferienwohnungs_id);
            }).filter(fw => fw !== undefined) as Ferienwohnung[];
            console.log(mappedData)
            setFilteredFerienwohnungen(mappedData);
            setFilteredFerienwohnungenLoading(false);
        } catch (error) {
            setFilteredFerienwohnungenError('Failed to fetch filtered Ferienwohnungen');
            setFilteredFerienwohnungenLoading(false);
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
        <FerienwohnungContext.Provider value={{
            ferienwohnungen,
            ferienwohnungenLoading,
            ferienwohnungenError,
            filteredFerienwohnungen,
            filteredFerienwohnungenLoading,
            filteredFerienwohnungenError,
            addFerienwohnung,
            refreshFerienwohnungen,
            filterFerienwohnungen,
        }}>
            {children}
        </FerienwohnungContext.Provider>
    );
};
