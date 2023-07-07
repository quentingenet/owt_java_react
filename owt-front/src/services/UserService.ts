import { ILoginForm } from "../models/ILoginForm";

export const login = (data: ILoginForm) => {
    const requestData = {appUsername: data.username, password: data.password}
    fetch('http://localhost:7777/api/v1/login',{
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(requestData)
  })
    .then(response => {if (response.ok) {
        const headers = response.headers;
        const jwt = headers.get('Authorization') || "";
        localStorage.setItem('jwt', jwt);
        return response.json();
      } else {
        throw new Error('Erreur lors de la requête à l\'API');
      }
    })
    .catch(error => {

      console.error('Erreur lors de l\'appel à l\'API :', error);
    });
};