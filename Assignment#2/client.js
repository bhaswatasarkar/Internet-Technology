const socket = io('http://localhost:8000');

const {username, roomname} = Qs.parse(location.search,{//username and the room for multicast
    ignoreQueryPrefix: true
});

const form = document.getElementById('send-container')//form to collect data for sending message
const broadcastbutton = document.querySelector('#broadcastbutton')
const messageInput = document.getElementById('messageInp')
const broadcastoutput = document.querySelector('#broadcastcontainer')
 

const appendelement = (message,messageclassdesign,castingtype)=>{
    const newelement = document.createElement('div')
    newelement.innerText = message
    newelement.classList.add(messageclassdesign)
    if(castingtype=='broadcast')
        broadcastoutput.append(newelement)
}

// const username = prompt("Enter your name to join")
socket.emit('new-user-joined',({username,roomname}))

socket.on('user-joined',username=>{
    appendelement(`${username} joined the chat`,'left','broadcast')
})

form.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','broadcast')
    socket.emit('send',message)
    messageInput.value=''
})



socket.on('receive',data=>{
    appendelement(`${data.user.username}: ${data.message}`,'left','broadcast')
})
