let enrolledArray = [];
let offeredArray = [];

startup();

/*
 * Ran in an async function to make getOfferedCourses wait for
 * getEnrolledCourses. If it does not wait, we cannot ensure that
 * courses that are enrolled are removed from the offered list
 */
async function startup(){
    await getEnrolledCourses();
    getOfferedCourses();
}

async function getEnrolledCourses() {

    /*
     * Retrieves the email passed as a param in the url to send
     * to the backend. There is definitely a better way of doing this
     * but for now this is the work around I'm using.
     */
    const enrolledList = document.querySelector('.enrolledList');
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const email = urlParams.get('email');

    let emailMap = {
        email: email
    }

    const url = 'https://dawg-chat.onrender.com/enrolledCourses';
    const response = await fetch(url, {
        method: 'POST',
        body: JSON.stringify(emailMap),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });

    //Creates a list of all enrolled courses
    const data = await response.json();
    data.forEach(function (item, index) {
        let listElement = document.createElement("li");
        listElement.setAttribute("id", item);
        listElement.appendChild(document.createTextNode(item));
        listElement.addEventListener('click', removeCourse);
        enrolledList.appendChild(listElement);
        enrolledArray.push(item);
    });
}

async function getOfferedCourses() {


    const offeredList = document.querySelector('.offeredList');

    const url = 'https://dawg-chat.onrender.com/offeredCourses';
    const response = fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    })
        .then((response) => response.json())
        .then((data) => {
            //Creates a list of all offered courses
            data.forEach(function (item, index) {
                if(!enrolledArray.includes(item)){
                    let listElement = document.createElement("li");
                    listElement.setAttribute("id", item);
                    listElement.addEventListener('click', addCourse);
                    listElement.appendChild(document.createTextNode(item));
                    offeredList.appendChild(listElement);
                }
            });
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

//Called when a course in the enrolled list is clicked
function removeCourse(event){
    const course_number = event.target.innerText;

    //Removes course from array of enrolled courses
    let index = enrolledArray.indexOf(course_number);
    if (index !== -1) {
        enrolledArray.splice(index, 1);
    }

    /*
     * Course is removed from the enrolled list on the html page.
     * Then it is placed in the offered array.
     * Finally, it is placed in the offered list on the html page.
     */
    const list = document.querySelector('.enrolledList');
    const listItem = document.getElementById(course_number);
    list.removeChild(listItem);

    offeredArray.push(course_number);

    let listElement = document.createElement("li");
    listElement.setAttribute("id", course_number);
    listElement.addEventListener('click', addCourse);
    listElement.appendChild(document.createTextNode(course_number));
    const offeredList = document.querySelector('.offeredList');
    offeredList.appendChild(listElement);

    /*
     * Retrieves the email passed as a param in the url to send
     * to the backend. There is definitely a better way of doing this
     * but for now this is the work around I'm using.
     */
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const email = urlParams.get('email');

    let credentials = {
        email: email,
        course_number: course_number
    }

    const url = 'https://dawg-chat.onrender.com/removeCourse';
    const response = fetch(url, {
        method: 'POST',
        body: JSON.stringify(credentials),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data)
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function addCourse(event){

    const course_number = event.target.innerText;

    //Removes course from array of offered courses
    let index = offeredArray.indexOf(course_number);
    if (index !== -1) {
        offeredArray.splice(index, 1);
    }

    /*
     * Course is removed from the offered list on the html page.
     * Then it is placed in the enrolled array.
     * Finally, it is placed in the enrolled list on the html page.
     */
    const list = document.querySelector('.offeredList');
    const listItem = document.getElementById(course_number);
    list.removeChild(listItem);

    enrolledArray.push(course_number);

    let listElement = document.createElement("li");
    listElement.setAttribute("id", course_number);
    listElement.addEventListener('click', removeCourse);
    listElement.appendChild(document.createTextNode(course_number));
    const enrolledList = document.querySelector('.enrolledList');
    enrolledList.appendChild(listElement);

    /*
     * Retrieves the email passed as a param in the url to send
     * to the backend. There is definitely a better way of doing this
     * but for now this is the work around I'm using.
     */
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const email = urlParams.get('email');

    let credentials = {
        email: email,
        course_number: course_number
    }

    const url = 'https://dawg-chat.onrender.com/addCourse';
    const response = fetch(url, {
        method: 'POST',
        body: JSON.stringify(credentials),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data)
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}