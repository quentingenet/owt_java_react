import './App.css';
import NavbarConnected from './components/NavbarConnected/NavbarConnected.tsx';
import Navbar from './components/Navbar/Navbar.tsx';
import { useUserContext } from './contexts/UserContext.tsx';
import Landpage from './pages/Landpage/Landpage.tsx';

function App() {
	const userContext = useUserContext();

	return (
		<>
			{userContext.isUserLoggedIn ? <NavbarConnected /> : <Navbar />}
			<Landpage />
		</>
	);
}

export default App;
