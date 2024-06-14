import React, { createContext, useContext, useState, useEffect, PropsWithChildren } from 'react';
import { fetchFerienwohnungen, fetchKunden, addFerienwohnung as apiAddFerienwohnung, addKunde as apiAddKunde } from '../api/api';
import { Ferienwohnung, Kunde } from '../types';

interface FerienwohnungContextType {
    ferienwohnungen: Ferienwohnung[];
    ferienwohnungenLoading: boolean;
    ferienwohnungenError: string | null;
    addFerienwohnung: (newFerienwohnung: Ferienwohnung) => Promise<void>;
    refreshFerienwohnungen: () => Promise<void>;
}

interface KundeContextType {
    kunden: Kunde[];
    kundenLoading: boolean;
    kundenError: string | null;
    addKunde: (newKunde: Kunde) => Promise<void>;
    refreshKunden: () => Promise<void>;
}

type EntityContextType = FerienwohnungContextType & KundeContextType;

const EntitiesContext = createContext<EntityContextType | undefined>(undefined);

export const useEntitiesContext = () => {
    const context = useContext(EntitiesContext);
    if (!context) {
        throw new Error('useEntitiesContext must be used within an EntitiesProvider');
    }
    return context;
};

export const EntitiesProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [ferienwohnungen, setFerienwohnungen] = useState<Ferienwohnung[]>([]);
    const [ferienwohnungenLoading, setFerienwohnungenLoading] = useState(true);
    const [ferienwohnungenError, setFerienwohnungenError] = useState<string | null>(null);

    const [kunden, setKunden] = useState<Kunde[]>([]);
    const [kundenLoading, setKundenLoading] = useState(true);
    const [kundenError, setKundenError] = useState<string | null>(null);

    const fetchEntities = async () => {
        try {
            const ferienwohnungenData = await fetchFerienwohnungen();
            setFerienwohnungen(ferienwohnungenData);
            setFerienwohnungenLoading(false);
        } catch (err) {
            setFerienwohnungenError('Failed to fetch Ferienwohnungen');
            setFerienwohnungenLoading(false);
        }

        try {
            const kundenData = await fetchKunden();
            setKunden(kundenData);
            setKundenLoading(false);
        } catch (err) {
            setKundenError('Failed to fetch Kunden');
            setKundenLoading(false);
        }
    };

    useEffect(() => {
        fetchEntities();
    }, []);

    const refreshFerienwohnungen = async (): Promise<void> => {
        try {
            const updatedFerienwohnungen = await fetchFerienwohnungen();
            setFerienwohnungen(updatedFerienwohnungen);
        } catch (error) {
            setFerienwohnungenError('Failed to refresh Ferienwohnungen');
        }
    };

    const refreshKunden = async (): Promise<void> => {
        try {
            const updatedKunden = await fetchKunden();
            setKunden(updatedKunden);
        } catch (error) {
            setKundenError('Failed to refresh Kunden');
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

    const addKunde = async (newKunde: Kunde): Promise<void> => {
        try {
            await apiAddKunde(newKunde);
            await refreshKunden();
        } catch (error) {
            throw new Error(`Failed to add Kunde: ${error}`);
        }
    };

    const contextValue: EntityContextType = {
        ferienwohnungen: ferienwohnungen,
        ferienwohnungenLoading: ferienwohnungenLoading,
        ferienwohnungenError: ferienwohnungenError,
        addFerienwohnung: addFerienwohnung,
        refreshFerienwohnungen: refreshFerienwohnungen,
        kunden: kunden,
        kundenLoading: kundenLoading,
        kundenError: kundenError,
        addKunde: addKunde,
        refreshKunden: refreshKunden,
    };

    return (
        <EntitiesContext.Provider value={contextValue}>
            {children}
        </EntitiesContext.Provider>
    );
};
