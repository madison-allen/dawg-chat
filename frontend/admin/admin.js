
const messageDiv = document.querySelector('.message');

function createCourse(){

    const course_number = document.getElementById('courseNumber').value;
    const course_name = document.getElementById('courseName').value;

    let course = {
        course_number: course_number,
        course_name: course_name
    }

    const url = 'http://localhost:8080/createCourse';
    const response = fetch(url, {
        method: 'POST',
        body: JSON.stringify(course),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            if ('OK' in data) {
                document.querySelector('.errorMessage').innerHTML = '';
                document.querySelector('.successMessage').innerHTML = data.OK;
            }
            else {
                document.querySelector('.successMessage').innerHTML = '';
                //Displays error pertaining to why the request did not succeed
                document.querySelector('.errorMessage').innerHTML = data.BAD_REQUEST;
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function removeCourse(){
    const course_number = document.getElementById('courseNumber').value;

    let course = {
        course_number: course_number
    }

    const url = 'http://localhost:8080/deleteCourse';
    const response = fetch(url, {
        method: 'POST',
        body: JSON.stringify(course),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            if ('OK' in data) {
                document.querySelector('.errorMessage').innerHTML = '';
                document.querySelector('.successMessage').innerHTML = data.OK;
            }
            else {
                document.querySelector('.successMessage').innerHTML = '';
                //Displays error pertaining to why the request did not succeed
                document.querySelector('.errorMessage').innerHTML = data.BAD_REQUEST;
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}