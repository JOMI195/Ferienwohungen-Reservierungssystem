import { Routes, Route } from 'react-router-dom';
import MainLayout from '@/common/components/layout/layout';
import NotFound from '@/common/components/error/notFound/notFound';
import Search from '@/main/search/search';
import ApartmentDetail from '@/main/search/apartmentDetail/apartmentDetail';

const Routing = () => {
  return (
    <Routes>
      <Route path="*" element={<NotFound />} />
      <Route element={<MainLayout />}>
        <Route path="/" element={<Search />} />
        <Route path="/apartment/:id" element={<ApartmentDetail />} />
      </Route>
    </Routes>
  );
};

export default Routing;
