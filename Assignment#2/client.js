// const e = require("express");

const socket = io('http://localhost:8000');

const {username, roomname} = Qs.parse(location.search,{//username and the room for multicast
    ignoreQueryPrefix: true
});


const messageInput = document.getElementById('messageInp')//message send

//broadcast variables
const form = document.getElementById('send-container')
const broadcastbutton = document.querySelector('#broadcastbutton')
const broadcastoutput = document.querySelector('#broadcastcontainer')

//multicast variables
const formmulticast = document.getElementById('sender-form-multicast')
const multicastbutton = document.querySelector('#multicastbutton')
const multicastoutput = document.querySelector('#multicastcontainer')

//unicast variables
const formunicast = document.getElementById('sender-form-unicast')
const unicastbutton = document.querySelector('#unicastbutton')
const unicastoutput = document.querySelector('#unicastcontainer')

const appendelement = (message,messageclassdesign,castingtype)=>{
    const newelement = document.createElement('div')
    newelement.innerText = message
    newelement.classList.add(messageclassdesign)
    if(castingtype=='broadcast')
        broadcastoutput.append(newelement)
    else if(castingtype=='multicast'){
        multicastoutput.append(newelement)
    }
    
}


const appendunicastelement = (message,messageclassdesign,username)=>{
    //const unicastoutput = document.querySelector(`#`+`${username}`)
    const unicastnewuser = document.getElementById(`${username}`)
    const newelement = document.createElement('div')
    newelement.innerText = message
    newelement.classList.add(messageclassdesign)
    unicastnewuser.append(newelement)
}



socket.emit('new-user-joined',({username,roomname}))

socket.on('user-joined',username=>{
    appendelement(`${username} joined the chat`,'left','broadcast')
})



form.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','broadcast')
    socket.emit('sendbroadcast',message)
    messageInput.value=''
})

formmulticast.addEventListener('submit',(e)=>{
    e.preventDefault();
    const message = messageInput.value
    appendelement(`You: ${message}`,'right','multicast')
    socket.emit('sendmulticast',message)
    messageInput.value=''
})

formunicast.addEventListener('submit',(e)=>{
    e.preventDefault();
    const nametosend = document.getElementById("nametosendunicast").value

    const message = messageInput.value
    const newelement = document.createElement('div')

    if(document.getElementById(`${nametosend}`)===null){
        newelement.innerHTML=`<p>
            <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample">
            ${nametosend}
            </button>
        </p>
        <div style="min-height: 120px;">
            <div class="collapse collapse-horizontal" id="collapseWidthExample">
            <div class="card card-body" style="width: 300px;" id="${nametosend}">
                This is some placeholder content for a horizontal collapse. It's hidden by default and shown when triggered.
            </div>
            </div>
        </div>`
        console.log('here i am')
        unicastoutput.appendChild(newelement)
    }
    appendunicastelement(`You: ${message}`,'right',nametosend)
    socket.emit('sendunicast',{message,nametosend})
    messageInput.value=''
})




socket.on('receive',data=>{
    appendelement(`${data.user.username}: ${data.message}`,'left','broadcast')
})

socket.on('receivemulticast',data=>{
    appendelement(`${data.user.username}: ${data.message}`,'left','multicast')
})

socket.on('receiveunicast',data=>{
    console.log('Here1')
    namefromreceived = data.user.username
    console.log('Here2'+namefromreceived)

    const newelement = document.createElement('div')
    if(document.getElementById(`${namefromreceived}`)===null){
        newelement.innerHTML=`<p>
            <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#collapseWidthExample" aria-expanded="false" aria-controls="collapseWidthExample">
            ${namefromreceived}
            </button>
        </p>
        <div style="min-height: 120px;">
            <div class="collapse collapse-horizontal" id="collapseWidthExample">
            <div class="card card-body" style="width: 300px;" id="${namefromreceived}">
                This is some placeholder content for a horizontal collapse. It's hidden by default and shown when triggered.
            </div>
            </div>
        </div>`
        console.log('Here3')
        unicastoutput.appendChild(newelement)
        console.log('Here4')
    }

    appendunicastelement(`${data.user.username}: ${data.message}`,'left',namefromreceived)

    console.log('Here5')

})
