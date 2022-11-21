
let email = getEmail();
const apiToken = generateToken();
setChatTitle();

/*
 * Takes the course number from the url because it is passed as
 * a parameter. It then adds the course number to the title of 
 * the chat page.
 */
function setChatTitle(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const course_number = urlParams.get('course_number');

    const label = document.querySelector(".label");
    label.innerHTML = course_number + " Chat"
}

/*
 * Retrieves the email passed as a param in the url to send
 * to the backend. There is definitely a better way of doing this
 * but for now this is the work around I'm using.
 */
function getEmail() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get('email');
}

/*
 * Calls the backend API endpoint to generate a uninque token for the
 * user of the given email. This token is returned and placed in the
 * token variable to be used for sending messages with the Stream API
 */
function generateToken(){

    let token;

    let user = {
        email: email
    }

    const url = 'https://dawg-chat.onrender.com/createToken';
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