import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";

const MainLayout: React.FC = () => {
    return (
        <Box sx={{ display: "flex", height: "100vh", overflow: "hidden" }}>
            <Box sx={{ flexGrow: 1, overflow: "auto" }}>
                <Outlet />
            </Box>
        </Box>
    );
};

export default MainLayout;
