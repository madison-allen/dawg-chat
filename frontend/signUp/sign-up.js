const submit = document.getElementById('submit');

function submitRequest(event) {
    event.preventDefault();
    const form = new FormData(event.target);

    const email = form.get("email");

    let user = {
        student_id: form.get("student_id"),
        first_name: form.get("first_name"),
        last_name: form.get("last_name"),
        email: email,
        password: form.get("password")
    }

    const url = 'https://dawg-chat.onrender.com/signup';
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
            if('OK' in data){
                //Sends to courses page with email as url parameter if successful
                window.location.href = "../courses/courses.html?email=" + email;
            }
            else{
                //Displays error pertaining to why the request did not succeed
                document.querySelector('.errorMessage').innerHTML = data.BAD_REQUEST;
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

