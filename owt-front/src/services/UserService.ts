import { ILoginForm } from "../models/ILoginForm";
import { API_URL } from "../utils/GlobalUtils";

export const login = (data: ILoginForm) => {
  const requestData = { appUsername: data.username, password: data.password };

  return new Promise((resolve, reject) => {
    fetch(API_URL.concat('login'), {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestData)
    })
    .then(response => {
      if (response.ok) {
        const headers = response.headers;
        const jwt = headers.get('Authorization') || "";
        if (localStorage.getItem('jwt')) {
          localStorage.removeItem('jwt');
        }
        localStorage.setItem('jwt', jwt);
        resolve(response.json());
      } else {
        throw new Error('Erreur lors de la requête à l\'API');
      }
    })
    .catch(error => {
      reject(new Error('Erreur lors de l\'appel à l\'API :' + error));
    });
  });
};