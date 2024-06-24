import { Box, Breadcrumbs, CardMedia, Divider, Grid, IconButton, Link, List, ListItem, ListItemIcon, ListItemText, Rating, Typography } from '@mui/material';
import { useParams, Link as RouterLink } from 'react-router-dom';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useEntitiesContext } from '@/context/entities/useEntitiesContext';
import LocationMap from './locationMap/locationMap';
import { useEffect } from 'react';
import BookingForm from './bookingForm/bookingForm';
import { useBookingContext } from '@/context/booking/bookingContext';
import { Ausstattung, Besitzt, Ferienwohnung } from '@/types';

import {
    Balcony as BalconyIcon,
    Kitchen as KitchenIcon,
    Tv as TvIcon,
    HotTub as SaunaIcon,
    Wifi as WifiIcon,
    Yard as GardenIcon,
    OutdoorGrill as GrillIcon,
    Pool as PoolIcon,
    AcUnit as AcUnitIcon,
    Whatshot as HeatingIcon,
    Games as DartsIcon,
} from '@mui/icons-material';
type IconMap = Record<string, JSX.Element>;

const ApartmentDetail = () => {
    const { id: ferienwohnungs_id } = useParams<{ id: string }>();
    const {
        ferienwohnungen,
        bilder,
        ausstattungen,
        refreshFerienwohnungen,
        refreshBilder,
        refreshAusstattungen,
        refreshBesitzt,
        besitzt
    } = useEntitiesContext();
    const {
        setBookingFerienwohung
    } = useBookingContext();
    const selectedFerienwohnung = ferienwohnungs_id !== undefined ? ferienwohnungen.find(ferienwohnung => ferienwohnung.ferienwohnungs_id === +ferienwohnungs_id) : undefined;
    const bild = ferienwohnungs_id !== undefined ? bilder.find(bild => bild.ferienwohnungs_id === +ferienwohnungs_id) : undefined;

    const renderAusstattungListItems = (ausstattungen: Ausstattung[], besitzt: Besitzt[], selectedFerienwohnung: Ferienwohnung) => {
        const iconMap: IconMap = {
            Balkon: <BalconyIcon />,
            Küche: <KitchenIcon />,
            TV: <TvIcon />,
            Sauna: <SaunaIcon />,
            WLAN: <WifiIcon />,
            Garten: <GardenIcon />,
            Grillplatz: <GrillIcon />,
            Pool: <PoolIcon />,
            Klimaanlage: <AcUnitIcon />,
            Heizung: <HeatingIcon />,
            Dartscheibe: <DartsIcon />,
        };
        const filteredAusstattungen = ausstattungen.filter(ausstattungItem =>
            besitzt.some(besitztItem =>
                besitztItem.ferienwohnungs_id === selectedFerienwohnung.ferienwohnungs_id &&
                besitztItem.ausstattungsname === ausstattungItem.ausstattungsname
            )
        );

        return filteredAusstattungen.map(ausstattungItem => (
            <ListItem disablePadding key={ausstattungItem.ausstattungsname}>
                <ListItemIcon>
                    {iconMap[ausstattungItem.ausstattungsname]}
                </ListItemIcon>
                <ListItemText primary={ausstattungItem.ausstattungsname} />
            </ListItem>
        ));
    };

    useEffect(() => {
        if (ferienwohnungen.length === 0) {
            refreshFerienwohnungen();
            refreshBilder();
            refreshAusstattungen();
        }
        refreshBesitzt();
    }, []);

    useEffect(() => {
        if (selectedFerienwohnung) setBookingFerienwohung(selectedFerienwohnung);
    }, [selectedFerienwohnung]);

    if (!selectedFerienwohnung) {
        return <Typography>No apartment found with ID: {ferienwohnungs_id}</Typography>;
    }

    return (
        <Box p={5}>
            <Box
                sx={{ display: "flex", justifyContent: "flex-start", alignItems: "center" }}
            >
                <IconButton sx={{ mr: 1 }} aria-label="delete" size="medium" component={RouterLink} to="/">
                    <ArrowBackIcon />
                </IconButton>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link sx={{ textDecoration: 'none' }} component={RouterLink} to="/">
                        <Typography color="GrayText">Search</Typography>
                    </Link>
                    <Typography color="primary">Detail</Typography>
                </Breadcrumbs>
            </Box>
            <Box sx={{ mt: 5 }}>
                <Typography variant="h3">{selectedFerienwohnung.ferienwohnungsname}</Typography>
                <Box sx={{ display: "flex", justifyContent: "flex-start", alignItems: "center" }}>
                    <Box sx={{ display: 'flex', justifyContent: "center" }}>
                        <Typography sx={{ mr: 1 }} variant="body2">
                            {`${selectedFerienwohnung.straße} ${selectedFerienwohnung.hausnummer}, ${selectedFerienwohnung.postleitzahl} ${selectedFerienwohnung.ort}, ${selectedFerienwohnung.landname}`}
                        </Typography>
                        <Rating size="small" value={selectedFerienwohnung.avgSterne} readOnly />
                    </Box>
                </Box>
                <Grid container spacing={2} sx={{ my: 5 }}>
                    <Grid item xs={12} md={8}>
                        <CardMedia
                            component="img"
                            image={bild != null ? bild.linkURL : "/imagePlaceholder.svg"}
                            alt={bild != null ? "Image of the apartment" : "Default apartment banner"}
                            sx={{
                                objectFit: "cover",
                                objectPosition: "center center",
                                borderRadius: theme => theme.shape.borderRadius,
                                width: '100%',
                                maxHeight: 500
                            }}
                        />
                    </Grid>
                    <Grid item xs={12} md={4}>
                        <BookingForm selectedFerienwohnung={selectedFerienwohnung} />
                    </Grid>
                </Grid>
                <Divider sx={{ my: 2, width: "100%", borderBottomWidth: 1 }} />
                <Box>
                    <Typography variant="h5">{"Das bietet Ihnen die Unterkunft"}</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} md={5}>
                            <List>
                                {renderAusstattungListItems(ausstattungen, besitzt, selectedFerienwohnung)}
                            </List>
                        </Grid>
                    </Grid>
                </Box>
                <Divider sx={{ my: 2, width: "100%", borderBottomWidth: 1 }} />
                <Box>
                    <Typography variant="h5">{"Hier machen Sie Urlaub"}</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} md={6}>
                            <LocationMap address={`${selectedFerienwohnung.straße} ${selectedFerienwohnung.hausnummer}, ${selectedFerienwohnung.postleitzahl} ${selectedFerienwohnung.ort}, ${selectedFerienwohnung.landname}`} />
                        </Grid>
                        <Grid item xs={12} md={6}>
                            <List>

                            </List>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Box >
    );
};

export default ApartmentDetail;
