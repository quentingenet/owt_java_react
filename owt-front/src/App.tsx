import './App.css';
import NoMatch from './components/404/NoMatch.tsx';
import { useUserContext } from './contexts/UserContext.tsx';
import Dashboard from './pages/Dashboard/Dashboard.tsx';
import Landpage from './pages/Landpage/Landpage.tsx';
import { Routes, Route } from 'react-router-dom';
import NavbarConnected from './components/NavbarConnected/NavbarConnected.tsx';
import NavbarPublic from './components/NavbarPublic/NavbarPublic.tsx';
import Protected from './routing/Protected.tsx';

function App() {
	const userContext = useUserContext();
	console.log('IS USER LOGGED IN', userContext.isUserLoggedIn);
	console.log('JWT FROM APP =', userContext.jwt);

	return (
		<>
			{userContext.isUserLoggedIn ? <NavbarConnected /> : <NavbarPublic />}
			<Routes>
				<Route
					path="/"
					element={userContext.isUserLoggedIn ? <Dashboard /> : <Landpage />}
				/>
				<Route
					path="/dashboard"
					element={<Protected>{userContext.isUserLoggedIn && <Dashboard />}</Protected>}
				/>

				<Route path="*" element={<NoMatch />} />
			</Routes>
		</>
	);
}

export default App;
