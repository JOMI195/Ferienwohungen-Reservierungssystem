import { Box, Button, FormControl, Grid, InputLabel, MenuItem, Select, Typography } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import React, { useState } from 'react';

interface FilterBarProps {
    laender: Array<{ landname: string }>;
    ausstattungen: Array<{ ausstattungsname: string }>;
    filterFerienwohnungen: (landname: string, ausstattung: string, startdatum: string, enddatum: string) => void;
    setIsFiltered: (value: boolean) => void;
}

const FilterBar: React.FC<FilterBarProps> = ({
    laender,
    ausstattungen,
    filterFerienwohnungen,
    setIsFiltered
}) => {
    const [landname, setLandname] = useState('');
    const [ausstattung, setAusstattung] = useState('');
    const [startdatum, setStartdatum] = useState<dayjs.Dayjs | null>(null);
    const [enddatum, setEnddatum] = useState<dayjs.Dayjs | null>(null);

    const handleFilterButtonClicked = () => {
        filterFerienwohnungen(
            landname,
            ausstattung,
            startdatum ? startdatum.format('YYYY-MM-DD') : '',
            enddatum ? enddatum.format('YYYY-MM-DD') : ''
        );
        setIsFiltered(true);
    };

    const handleStartDateChange = (date: dayjs.Dayjs | null) => {
        if (date) {
            const newStartdatum = dayjs(date);
            setStartdatum(newStartdatum);
            const newEnddatum = newStartdatum.add(1, 'day');
            setEnddatum(newEnddatum);
        } else {
            setStartdatum(null);
            setEnddatum(null);
        }
    };

    const handleEndDateChange = (date: dayjs.Dayjs | null) => {
        setEnddatum(date ? dayjs(date) : null);
    };

    const disableDatesBeforeOrExactStartDate = (date: dayjs.Dayjs) => {
        if (!startdatum) return false; // No startdatum selected, allow all dates
        return dayjs(date).isSame(dayjs(startdatum), 'day') || dayjs(date).isBefore(dayjs(startdatum), 'day');
    };

    return (
        <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <Typography variant="h4" sx={{ pb: 2 }}>Suche freie Ferienwohnungen</Typography>
            <Grid sx={{ px: 2 }} container spacing={2} alignItems="stretch">
                <Grid item xs={12} sm={6} md={2}>
                    <FormControl fullWidth>
                        <InputLabel id="land-select-label">Land</InputLabel>
                        <Select
                            labelId="land-select"
                            id="land-select"
                            value={landname}
                            onChange={(e) => setLandname(e.target.value as string)}
                            label="Land"
                        >
                            <MenuItem value="">None</MenuItem>
                            {laender.map((land) => (
                                <MenuItem key={land.landname} value={land.landname}>{land.landname}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12} sm={6} md={2}>
                    <FormControl fullWidth>
                        <InputLabel id="ausstattung-select-label">Ausstattung</InputLabel>
                        <Select
                            labelId="ausstattung-select"
                            id="ausstattung-select"
                            value={ausstattung}
                            onChange={(e) => setAusstattung(e.target.value as string)}
                            label="Ausstattung"
                        >
                            <MenuItem value="">None</MenuItem>
                            {ausstattungen.map((ausstattung) => (
                                <MenuItem key={ausstattung.ausstattungsname} value={ausstattung.ausstattungsname}>{ausstattung.ausstattungsname}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                    <DatePicker
                        format='DD-MM-YYYY'
                        label="Startdatum"
                        value={startdatum}
                        disablePast
                        onChange={(date) => handleStartDateChange(date)}
                        sx={{ width: '100%' }}
                        slotProps={{
                            field: { clearable: true },
                            day: {
                                sx: {
                                    "&.MuiPickersDay-root.Mui-disabled": {
                                        backgroundColor: "#EDEDED",
                                    },
                                },
                            },
                        }}
                    />
                </Grid>
                <Grid item xs={12} sm={6} md={3}>
                    <DatePicker
                        format='DD-MM-YYYY'
                        label="Enddatum"
                        value={enddatum}
                        onChange={(date) => handleEndDateChange(date)}
                        disablePast
                        shouldDisableDate={disableDatesBeforeOrExactStartDate}
                        sx={{ width: '100%' }}
                        slotProps={{
                            field: { clearable: true },
                            day: {
                                sx: {
                                    "&.MuiPickersDay-root.Mui-disabled": {
                                        backgroundColor: "#EDEDED",
                                    },
                                },
                            },
                        }}
                    />
                </Grid>
                <Grid item xs={12} sm={6} md={2} sx={{ display: 'flex', alignItems: 'stretch' }}>
                    <Button
                        sx={{ width: '100%', px: 2 }}
                        variant="contained"
                        onClick={handleFilterButtonClicked}
                    >
                        Suchen
                    </Button>
                </Grid>
            </Grid>
        </Box>
    );
};

export default FilterBar;
