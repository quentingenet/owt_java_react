import './App.css';
import NavbarConnected from './components/NavbarConnected/NavbarConnected.tsx';
import NavbarPublic from './components/NavbarPublic/NavbarPublic.tsx';
import { useUserContext } from './contexts/UserContext.tsx';
import Landpage from './pages/Landpage/Landpage.tsx';
import { useEffect } from 'react';

function App() {
	const userContext = useUserContext();
	useEffect(() => {
		const jwt: string = localStorage.getItem('jwt') || '';
		userContext.jwt = jwt;
	}, [userContext.isUserLoggedIn]);

	return (
		<>
			{userContext.isUserLoggedIn ? <NavbarConnected /> : <NavbarPublic />}
			<Landpage />
		</>
	);
}

export default App;
