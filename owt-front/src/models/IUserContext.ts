export interface IUserContext {
    readonly id: number;
    email: string;
    username: string;
    isUserLoggedIn: boolean;
}