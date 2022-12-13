
let email = getEmail();
let adminToken;
setChatTitle();
startup();

async function startup(){
    adminToken = await generateToken("admin");
    const student_id = await getStudentId();
    const apiToken = await generateToken(student_id);
    messaging(apiToken, student_id);
}

async function messaging(apiToken, student_id){
    let client = StreamChat.getInstance("a6x5uxw347tt");
    // you can still use new StreamChat("api_key");

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    await client.connectUser(
        {
            id: "admin",
            name: "admin"
        },
        adminToken,
    );

    let channel = client.channel('messaging', urlParams.get('course_number'), {
        name: urlParams.get('course_number')
    });
    await channel.watch();
    channel.addMembers(['siu850000009', 'siu851234567']);
    await client.disconnect();
    
    await client.connectUser(
        {
            id: student_id,
            name: email
        },
        apiToken,
    );

    channel = client.channel('messaging', urlParams.get('course_number'), {})
    // fetch the channel state, subscribe to future updates
    const state = await channel.watch();
    
    const send = async function (text){
        await channel.sendMessage({
            text
        });
    }

    const textInput = document.querySelector('.messageInput');
    textInput.addEventListener('keypress', function (event) {
        if (event.key === 'Enter') {
            send(event.target.value);
        }
    });

    channel.on('message.new', event => {
        //console.log('Received a new message:', event.message.text);
        
        const message = document.createElement('div');
        message.setAttribute("class", "message");
        const sender = document.createElement('p');
        sender.setAttribute("class", "sender");
        const text = document.createElement('p');
        text.setAttribute("class", "text");
        sender.innerHTML = event.user.name;
        text.innerHTML += "\t" + event.message.text + "\n";
        message.appendChild(sender);
        message.appendChild(text);
        document.querySelector('.messageDisplay').appendChild(message);
        textInput.value = "";
    });
}







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
async function generateToken(student_id){

    let token;

    let user = {
        email: student_id
    }

    const url = 'https://dawg-chat.onrender.com/createToken';
    const response = await fetch(url, {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });
    
    const data = await response.json();
    token = data.OK;

    return token;
}

async function getStudentId() {

    let student_id;

    let user = {
        email: email
    }

    const url = 'http://localhost:8080/getStudentId';
    const response = await fetch(url, {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    });

    const data = await response.json();
    student_id = data.OK;

    return student_id;
}