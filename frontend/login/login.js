function validateUser(event) {
    event.preventDefault();
    const form = new FormData(event.target);

    const email = form.get("email");

    let login = {
        email: email,
        password: form.get("password")
    }

    const url = 'https://dawg-chat.onrender.com/login';
    const response = fetch(url, {
        method: 'POST',
        body: JSON.stringify(login),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    })
        .then((response) => response.json())
        .then((data) => {
            if ('OK' in data) {
                //Sends to a placeholder page if the post request was successful
                //Will be replaced once actual page is made
                window.location.href = "../courses/courses.html?email=" + email;
            }
            else {
                //Displays error pertaining to why the request did not succeed
                document.querySelector('.errorMessage').innerHTML = data.BAD_REQUEST;
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}