import { createContext, useContext, useState, PropsWithChildren } from 'react';
import { fetchBesitzt } from '../../api/api';
import { Besitzt } from '../../types'; // Define Besitzt type as per your needs

interface BesitztContextType {
    besitzt: Besitzt[];
    besitztLoading: boolean;
    besitztError: string | null;
    refreshBesitzt: () => Promise<void>;
}

const BesitztContext = createContext<BesitztContextType | undefined>(undefined);

export const useBesitztContext = () => {
    const context = useContext(BesitztContext);
    if (!context) {
        throw new Error('useBesitztContext must be used within a BesitztProvider');
    }
    return context;
};

export const BesitztProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    const [besitzt, setBesitzt] = useState<Besitzt[]>([]);
    const [besitztLoading, setBesitztLoading] = useState(false);
    const [besitztError, setBesitztError] = useState<string | null>(null);

    const refreshBesitzt = async (): Promise<void> => {
        try {
            setBesitztLoading(true);
            const updatedBesitzt = await fetchBesitzt();
            setBesitzt(updatedBesitzt);
            setBesitztLoading(false);
        } catch (error) {
            setBesitztError('Failed to refresh Besitzt data');
            setBesitztLoading(false);
        }
    };

    return (
        <BesitztContext.Provider value={{
            besitzt,
            besitztLoading,
            besitztError,
            refreshBesitzt,
        }}>
            {children}
        </BesitztContext.Provider>
    );
};