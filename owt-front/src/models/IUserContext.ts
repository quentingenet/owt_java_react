export interface IUserContext {
    readonly id: number;
    jwt: string;
    email: string;
    username: string;
    isUserLoggedIn: boolean;
}