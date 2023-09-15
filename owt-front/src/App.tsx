import './App.css';
import NoMatch from './components/404/NoMatch.tsx';
import { useUserContext } from './contexts/UserContext.tsx';
import Dashboard from './pages/Dashboard/Dashboard.tsx';
import Landpage from './pages/Landpage/Landpage.tsx';
import { Routes, Route } from 'react-router-dom';
import NavbarConnected from './components/NavbarConnected/NavbarConnected.tsx';
import Protected from './routing/Protected.tsx';
import Weights from './pages/Weights/Weights.tsx';
import Profile from './pages/Profile/Profile.tsx';
import Contact from './pages/Contact/Contact.tsx';

function App() {
    const userContext = useUserContext();

    return (
        <>
            {userContext.isUserLoggedIn && <NavbarConnected />}
            <Routes>
                <Route
                    path='/'
                    element={
                        userContext.isUserLoggedIn ? (
                            <Dashboard />
                        ) : (
                            <Landpage />
                        )
                    }
                />
                <Route
                    path='/dashboard'
                    element={
                        <Protected>
                            <Dashboard />
                        </Protected>
                    }
                />
                <Route
                    path='/weights'
                    element={
                        <Protected>
                           <Weights />
                        </Protected>
                    }
                />
                <Route
                    path='/profile'
                    element={
                        <Protected>
                           <Profile />
                        </Protected>
                    }
                />
                <Route
                    path='/contact'
                    element={
                           <Contact />
                    }
                />                                                          
                <Route path='*' element={<NoMatch />} />
            </Routes>
        </>
    );
}

export default App;
