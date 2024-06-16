import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchBilder, addBild as apiAddBild } from '../../api/api';
import { Bild } from '../../types';

interface BildContextType {
    bilder: Bild[];
    bilderLoading: boolean;
    bilderError: string | null;
    addBild: (newBild: Bild) => Promise<void>;
    refreshBilder: () => Promise<void>;
}

const BildContext = createContext<BildContextType | undefined>(undefined);

export const useBildContext = () => {
    const context = useContext(BildContext);
    if (!context) {
        throw new Error('useBildContext must be used within a BildProvider');
    }
    return context;
};

export const BildProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [bilder, setBilder] = useState<Bild[]>([]);
    const [bilderLoading, setBilderLoading] = useState(false);
    const [bilderError, setBilderError] = useState<string | null>(null);

    const refreshBilder = async (): Promise<void> => {
        try {
            setBilderLoading(true);
            const updatedBilder = await fetchBilder();
            setBilder(updatedBilder);
            setBilderLoading(false);
        } catch (error) {
            setBilderError('Failed to refresh Bilder');
            setBilderLoading(false);
        }
    };

    const addBild = async (newBild: Bild): Promise<void> => {
        try {
            await apiAddBild(newBild);
            await refreshBilder();
        } catch (error) {
            throw new Error(`Failed to add Bild: ${error}`);
        }
    };

    return (
        <BildContext.Provider value={{ bilder, bilderLoading, bilderError, addBild, refreshBilder }}>
            {children}
        </BildContext.Provider>
    );
};
