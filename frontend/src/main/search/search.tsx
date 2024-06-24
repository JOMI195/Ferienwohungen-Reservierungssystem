import { useEntitiesContext } from '@/context/entities/useEntitiesContext';
import { Box, Button, Card, CardMedia, Grid, Skeleton, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import AppartmentCard from './appartmentCard/appartmentCard';
import FilterBar from './filterBar/filterBar';
import SortBar from './sortBar/sortBar';

const Search = () => {
    const {
        ferienwohnungen,
        refreshFerienwohnungen,
        ferienwohnungenLoading,
        bilder,
        refreshBilder,
        ausstattungen,
        refreshAusstattungen,
        laender,
        refreshLaender,
        filterFerienwohnungen,
        filteredFerienwohnungen,
        filteredFerienwohnungenLoading
    } = useEntitiesContext();

    const [isFiltered, setIsFiltered] = useState(false);
    const [renderedFerienwohnungen, setRenderedFerienwohnungen] = useState(ferienwohnungen);

    const handleResetFilterButtonClicked = () => {
        setIsFiltered(false);
    };

    const handleSortedFerienwohnungen = (sortedFerienwohnungen: any[]) => {
        setRenderedFerienwohnungen(sortedFerienwohnungen);
    };

    useEffect(() => {
        refreshFerienwohnungen();
        refreshBilder();
        refreshAusstattungen();
        refreshLaender();
    }, []);

    useEffect(() => {
        setRenderedFerienwohnungen(isFiltered ? filteredFerienwohnungen : ferienwohnungen);
    }, [ferienwohnungen, filteredFerienwohnungen, isFiltered]);

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
                    <FilterBar
                        laender={laender}
                        ausstattungen={ausstattungen}
                        filterFerienwohnungen={filterFerienwohnungen}
                        setIsFiltered={setIsFiltered}
                        onResetFilter={handleResetFilterButtonClicked}
                    />
                </Box>
            </Card >
            <Box mt={5} p={5}>
                <Box sx={{ display: 'flex', flexDirection: 'row', justifyContent: "space-between", alignItems: "center", pb: 2 }}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: "flex-start" }}>
                        <Typography variant="h4" sx={{}}>{`${isFiltered ? "Gefilterte" : "Alle"} Ferienwohnungen`}</Typography>
                        <Typography variant="body1" sx={{}}>{`${renderedFerienwohnungen.length} Ferienwohnungen`}</Typography>
                    </Box>
                </Box>
                <Grid container alignItems="stretch" justifyContent="flex-end" sx={{ pb: 2 }}>
                    <Grid item xs={12} md={4}>
                        <SortBar
                            ferienwohnungen={renderedFerienwohnungen}
                            onSortedFerienwohnungen={handleSortedFerienwohnungen}
                        />
                    </Grid>
                </Grid>
                <Grid container alignItems="stretch" justifyContent="flex-start" spacing={3}>
                    {(ferienwohnungenLoading || filteredFerienwohnungenLoading) ? (
                        Array.from({ length: 6 }).map((_, index) => (
                            <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                                <Card elevation={5} sx={{ borderRadius: theme => theme.shape.borderRadius }}>
                                    <Skeleton variant="rectangular" height={200} />
                                    <Box p={2}>
                                        <Skeleton width="60%" />
                                        <Skeleton width="80%" />
                                    </Box>
                                </Card>
                            </Grid>
                        ))
                    ) : (
                        renderedFerienwohnungen.map(ferienwohnung => {
                            const bild = bilder.find((bild) => bild.ferienwohnungs_id === ferienwohnung.ferienwohnungs_id) || null;
                            return (<Grid item xs={12} sm={6} md={4} lg={3} key={ferienwohnung.ferienwohnungs_id}>
                                <AppartmentCard ferienwohnung={ferienwohnung} bild={bild} />
                            </Grid>);
                        })
                    )}
                </Grid>
            </Box>
        </Box>
    );
};

export default Search;
