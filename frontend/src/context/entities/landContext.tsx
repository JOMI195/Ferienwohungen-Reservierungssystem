import React, { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchLaender, addLand as apiAddLand } from '../../api/api';
import { Land } from '../../types';
interface LandContextType {
    laender: Land[];
    laenderLoading: boolean;
    laenderError: string | null;
    addLand: (newLand: Land) => Promise<void>;
    refreshLaender: () => Promise<void>;
}

const LandContext = createContext<LandContextType | undefined>(undefined);

export const useLandContext = () => {
    const context = useContext(LandContext);
    if (!context) {
        throw new Error('useLandContext must be used within a LandProvider');
    }
    return context;
};

export const LandProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [laender, setLaender] = useState<Land[]>([]);
    const [laenderLoading, setLaenderLoading] = useState(false);
    const [laenderError, setLaenderError] = useState<string | null>(null);

    const refreshLaender = async (): Promise<void> => {
        try {
            setLaenderLoading(true);
            const updatedLaender = await fetchLaender();
            setLaender(updatedLaender);
            setLaenderLoading(false);
        } catch (error) {
            setLaenderError('Failed to refresh Laender');
            setLaenderLoading(false);
        }
    };

    const addLand = async (newLand: Land): Promise<void> => {
        try {
            await apiAddLand(newLand); // Implement your apiAddLand function
            await refreshLaender();
        } catch (error) {
            throw new Error(`Failed to add Land: ${error}`);
        }
    };

    return (
        <LandContext.Provider value={{ laender, laenderLoading, laenderError, addLand, refreshLaender }}>
            {children}
        </LandContext.Provider>
    );
};
