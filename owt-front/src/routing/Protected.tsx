import { Navigate } from 'react-router-dom';
import { useUserContext } from '../contexts/UserContext';
function Protected(props: any) {
    const userContext = useUserContext();

    if (!userContext.isUserLoggedIn) {
        return <Navigate to='/' replace />;
    }
    return props.children;
}
export default Protected;
