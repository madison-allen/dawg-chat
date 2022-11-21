
let email = getEmail();
const apiToken = generateToken();
setChatTitle();

function setChatTitle(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const course_number = urlParams.get('course_number');

    const label = document.querySelector(".label");
    label.innerHTML = course_number + " Chat"
}

function getEmail() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get('email');
}

function generateToken(){

    let token;

    let user = {
        email: email
    }

    const url = 'http://localhost:8080/createToken';
    const response = fetch(url, {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    })
        .then((response) => response.json())
        .then((data) => {
            token = data;
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    return token;
}