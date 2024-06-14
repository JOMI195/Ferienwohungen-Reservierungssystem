import { Routes, Route } from 'react-router-dom';
import MainLayout from '@/common/components/layout/layout';
import NotFound from '@/common/components/error/notFound/notFound';
import Search from '@/main/search/search';

const Routing = () => {
  return (
    <Routes>
      <Route path="*" element={<NotFound />} />
      <Route element={<MainLayout />}>
        <Route path="/" element={<Search />} />
      </Route>
    </Routes>
  );
};

export default Routing;
