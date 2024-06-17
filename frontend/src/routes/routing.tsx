import { Routes, Route } from 'react-router-dom';
import MainLayout from '@/common/components/layout/layout';
import NotFound from '@/common/components/error/notFound/notFound';
import Search from '@/main/search/search';
import Apartment from '@/main/search/apartment/apartment';

const Routing = () => {
  return (
    <Routes>
      <Route path="*" element={<NotFound />} />
      <Route element={<MainLayout />}>
        <Route path="/" element={<Search />} />
        <Route path="/apartment/:id" element={<Apartment />} />
      </Route>
    </Routes>
  );
};

export default Routing;
