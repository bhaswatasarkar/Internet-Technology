const socket = io('http://localhost:8000');


const form = document.getElementById('send-container')
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

const nameuser = prompt("Enter your name to join")
socket.emit('new-user-joined',nameuser)

socket.on('user-joined',nameuser=>{
    appendelement(`${nameuser} joined the chat`,'left','broadcast')
})

form.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','broadcast')
    socket.emit('send',message)
    messageInput.value=''
})


socket.on('receive',info=>{
    appendelement(`${info.nameuser}: ${info.message}`,'left','broadcast')
})
