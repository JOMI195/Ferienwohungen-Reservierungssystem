import { useEntitiesContext } from "@/context/entitiesContext";
import { useEffect } from 'react';

const Search = () => {
    const { kunden, kundenLoading, kundenError, refreshKunden } = useEntitiesContext();

    useEffect(() => {
        refreshKunden();
    }, [refreshKunden]);

    if (kundenLoading) {
        return <div>Loading Kunden...</div>;
    }

    if (kundenError) {
        return <div>Error loading Kunden: {kundenError}</div>;
    }

    if (!kunden || kunden.length === 0) {
        return <div>No Kunden data available.</div>;
    }

    return (
        <div>
            <h2>Kunden List</h2>
            <ul>
                {kunden.map((kunde) => (
                    <li key={kunde.email}>
                        <strong>Email:</strong> {kunde.email}<br />
                        <strong>Vorname:</strong> {kunde.vorname}<br />
                        <strong>Nachname:</strong> {kunde.nachname}<br />
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Search;
