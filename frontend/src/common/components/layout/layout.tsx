import { Box, Container, useTheme } from "@mui/material";
import { Outlet } from "react-router-dom";
import TopAppbar from "./topLayout/topAppBar";
import Footer from "./bottomLayout/footer";

const MainLayout: React.FC = () => {
    const theme = useTheme();
    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            <TopAppbar />
            <Box
                sx={{
                    flex: '1 1 auto',
                    display: 'flex',
                    flexDirection: 'column',
                }}
            >
                <Container
                    disableGutters
                    maxWidth="xl"
                    sx={{
                        flex: '1 1 auto',
                        display: 'flex',
                        flexDirection: 'column',
                    }}
                >
                    <Outlet />
                </Container>
            </Box>
            <Footer />
        </Box>
    );
};

export default MainLayout;
