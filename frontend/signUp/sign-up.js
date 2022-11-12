const submit = document.getElementById('submit');

function submitRequest(event) {
    event.preventDefault();
    const form = new FormData(event.target);

    let user = {
        student_id: form.get("student_id"),
        first_name: form.get("first_name"),
        last_name: form.get("last_name"),
        email: form.get("email"),
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
                //Sends to a placeholder page if the post request was successful
                //Will be replaced once actual page is made
                window.location.href = "main-page.html";
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
