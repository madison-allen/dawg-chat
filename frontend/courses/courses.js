let enrolledArray = [];
let offeredArray = [];
let email = getEmail();

startup();

/*
 * Retrieves the email passed as a param in the url to send
 * to the backend. There is definitely a better way of doing this
 * but for now this is the work around I'm using.
 */
function getEmail(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get('email');
}

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
        createEnrolledListElement(item);
        enrolledArray.push(item);
    });
}

async function getOfferedCourses() {
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
                    createOfferredListElement(item);
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

    createOfferredListElement(course_number);

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

    createEnrolledListElement(course_number);

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

function createEnrolledListElement(course_number){
    const enrolledList = document.querySelector('.enrolledList');
    let listDiv = document.createElement("div");
    listDiv.setAttribute("class", "listDiv");
    listDiv.setAttribute("id", course_number)

    let listElement = document.createElement("li");
    listElement.addEventListener('click', removeCourse);
    listElement.appendChild(document.createTextNode(course_number));

    let messageButton = document.createElement("img");
    messageButton.setAttribute("class", "messageButton");
    messageButton.setAttribute("src", "../assets/messagingLogo.png");
    messageButton.addEventListener('click', () => {
        window.location.href = "../chat/chat.html?email=" + email + "&course_number=" + course_number;
    });

    listDiv.appendChild(listElement);
    listDiv.appendChild(messageButton);
    enrolledList.appendChild(listDiv);
}

function createOfferredListElement(course_number){
    const offeredList = document.querySelector('.offeredList');

    let listElement = document.createElement("li");
    listElement.setAttribute("id", course_number);
    listElement.addEventListener('click', addCourse);
    listElement.appendChild(document.createTextNode(course_number));

    offeredList.appendChild(listElement);
}

function openChatPage(){
    window.location.href = "../chat/chat.html?email=" + email;
}