import { useEntitiesContext } from '@/context/entities/useEntitiesContext';
import { Box, Button, Card, CardMedia, FormControl, Grid, InputLabel, MenuItem, Select, TextField, Typography, useMediaQuery, useTheme } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import { useEffect, useState } from 'react';
import AppartmentCard from './appartmentCard/appartmentCard';

const Search = () => {
    const theme = useTheme();
    const {
        ferienwohnungen,
        refreshFerienwohnungen,
        bilder,
        refreshBilder,
        ausstattungen,
        refreshAusstattungen,
        laender,
        refreshLaender
    } = useEntitiesContext();

    const isSm = useMediaQuery(theme.breakpoints.down('sm'));
    const currentDate = new Date();
    const tomorrow = new Date();
    const [isFiltered, setIsFiltered] = useState(false);

    useEffect(() => {
        refreshFerienwohnungen();
        refreshBilder();
        refreshAusstattungen();
        refreshLaender();
        console.log(bilder);
        console.log(ferienwohnungen);
    }, []);

    return (
        <Box>
            <Card
                elevation={5}
                sx={{
                    width: '100%',
                    borderRadius: theme => theme.shape.borderRadius,
                    borderTopLeftRadius: '0px',
                    borderTopRightRadius: '0px',
                    position: 'relative',
                }}
            >
                <CardMedia
                    component="img"
                    image={"/pageTitleBanner.jpg"}
                    alt="This is a sample image"
                    sx={{
                        objectFit: "cover",
                        height: 500,
                    }}
                />
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: '80%',
                        maxWidth: 1000,
                        zIndex: 1,
                        backgroundColor: theme => theme.palette.background.paper,
                        py: 2,
                        borderRadius: theme => theme.shape.borderRadius,
                        boxShadow: 4
                    }}
                >
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                        <Typography variant="h4" sx={{ pb: 2 }}>Suche freie Ferienwohnungen</Typography>
                        <Grid sx={{ px: 2 }} container spacing={2} alignItems="stretch">
                            <Grid item xs={12} sm={6} md={2}>
                                <FormControl fullWidth>
                                    <InputLabel id="land-select-label">Land</InputLabel>
                                    <Select
                                        labelId="land-select"
                                        id="land-select"
                                        value=""
                                        label="Land"
                                    >
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
                                        value=""
                                        label="Ausstattung"
                                    >
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
                                    defaultValue={dayjs(currentDate)}
                                    sx={{ width: '100%' }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={3}>
                                <DatePicker
                                    format='DD-MM-YYYY'
                                    label="Enddatum"
                                    defaultValue={dayjs(tomorrow)}
                                    sx={{ width: '100%' }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={2} sx={{ display: 'flex', alignItems: 'stretch' }}>
                                <Button sx={{ width: '100%', px: 2 }} variant="contained">Suchen</Button>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Card >
            <Box mt={5} p={5}>
                <Box sx={{ display: 'flex', flexDirection: 'row', justifyContent: "space-between", alignItems: "center", pb: 2 }}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: "flex-start", pb: 2 }}>
                        <Typography variant="h4" sx={{}}>{`${isFiltered ? "Gefilterte" : "Alle"} Ferienwohnungen`}</Typography>
                        <Typography variant="body1" sx={{}}>{`${isFiltered ? ferienwohnungen.length : ferienwohnungen.length} Ferienwohnungen`}</Typography>
                    </Box>
                    <Button sx={{ minHeight: "40px" }} color='secondary' variant='contained'>{"Alle anzeigen (Filter zurücksetzen)"}</Button>
                </Box>
                <Grid container alignItems="stretch" justifyContent="center" spacing={3}>
                    {ferienwohnungen.map((ferienwohnung) => {
                        const bild = bilder.find((bild) => bild.ferienwohnungs_Id === ferienwohnung.ferienwohnungs_Id) || null;
                        return (
                            <Grid item xs={12} sm={6} md={4} lg={3} key={ferienwohnung.ferienwohnungs_Id} style={{ display: 'flex' }}>
                                <AppartmentCard ferienwohnung={ferienwohnung} bild={bild} />
                            </Grid>
                        );
                    })}
                </Grid>
            </Box>
        </Box >
    );

};

export default Search;
