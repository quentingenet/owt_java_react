import { createContext, useContext } from 'react';
import { IUserContext } from '../models/IUserContext';

export const UserContext = createContext<IUserContext>({
	id: 0,
	email: '',
	username: '',
	isUserLoggedIn: false,
});

export function useUserContext() {
	return useContext(UserContext);
}

export function UserContextProvider({ children }: { children: React.ReactNode }) {
	/*
	useEffect(() => {
		let localUser = localStorage.getItem('userData') || '';
		if (localUser) {
			getUserById(JSON.parse(localUser).id).then((userData) => {
				id = userData.id;
				email = userData.email;
				username = userData.username;
				isUserLoggedIn = true;
			});
		}
	}, []);
*/
	const value = {
		id: 0,
		email: '',
		username: '',
		isUserLoggedIn: false,
	};

	return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}
