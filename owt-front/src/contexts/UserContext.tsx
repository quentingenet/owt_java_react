import { createContext, useContext } from 'react';
import { IUserContext } from '../models/IUserContext';

export const UserContext = createContext<IUserContext>({
	jwt: '',
	setJwt: () => {},
	isUserLoggedIn: false,
	setIsUserLoggedIn: () => {},
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
	const value: IUserContext = {
		jwt: '',
		setJwt: function (jwt: string): void {
			this.jwt = jwt;
		},
		isUserLoggedIn: false,
		setIsUserLoggedIn: function (isUserLoggedIn: boolean): void {
			this.isUserLoggedIn = isUserLoggedIn;
		},
	};

	return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}
