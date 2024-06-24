import * as React from 'react';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { Box } from '@mui/material';

interface SortBarProps {
    ferienwohnungen: any[];
    onSortedFerienwohnungen: (sortedFerienwohnungen: any[]) => void;
}

const SortBar: React.FC<SortBarProps> = ({ onSortedFerienwohnungen, ferienwohnungen }) => {
    const [sortOrder, setSortOrder] = React.useState<string>('');

    const handleSortOrderChange = (event: SelectChangeEvent) => {
        const newOrder = event.target.value as string;
        setSortOrder(newOrder);
        const sortedFerienwohnungen = sortFerienwohnungen(newOrder);
        onSortedFerienwohnungen(sortedFerienwohnungen);
    };

    const sortFerienwohnungen = (order: string) => {
        let sortedFerienwohnungen = [...ferienwohnungen];
        if (order === 'name-asc') {
            sortedFerienwohnungen = sortedFerienwohnungen.sort((a, b) => a.ferienwohnungsname.localeCompare(b.ferienwohnungsname));
        } else if (order === 'name-desc') {
            sortedFerienwohnungen = sortedFerienwohnungen.sort((a, b) => b.ferienwohnungsname.localeCompare(a.ferienwohnungsname));
        } else if (order === 'rating-asc') {
            sortedFerienwohnungen = sortedFerienwohnungen.sort((a, b) => a.avgSterne - b.avgSterne);
        } else if (order === 'rating-desc') {
            sortedFerienwohnungen = sortedFerienwohnungen.sort((a, b) => b.avgSterne - a.avgSterne);
        }
        return sortedFerienwohnungen;
    };

    return (
        <Box sx={{ width: "100%" }}>
            <FormControl fullWidth>
                <InputLabel id="select-sort-label">Sortieren nach</InputLabel>
                <Select
                    labelId="select-sort-label"
                    id="select-sort"
                    value={sortOrder}
                    label="Sortieren nach"
                    onChange={handleSortOrderChange}
                >
                    <MenuItem value={""}>Keine Sortierung</MenuItem>
                    <MenuItem value={"name-asc"}>Name aufsteigend</MenuItem>
                    <MenuItem value={"name-desc"}>Name absteigend</MenuItem>
                    <MenuItem value={"rating-asc"}>Bewertung aufsteigend</MenuItem>
                    <MenuItem value={"rating-desc"}>Bewertung absteigend</MenuItem>
                </Select>
            </FormControl>
        </Box>
    );
};

export default SortBar;
