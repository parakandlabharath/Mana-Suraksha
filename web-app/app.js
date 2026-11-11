const API_BASE = "http://localhost:8080/api/v1";
let activeType = "sms";
const input = document.querySelector("#user-input");
const label = document.querySelector("#input-label");
const analyseButton = document.querySelector("#analyse");
const resultBox = document.querySelector("#result");
const errorBox = document.querySelector("#error");
const config = {
  sms: {label:"Paste the message text", placeholder:"Paste a suspicious message here. Remove any OTP, PIN or password first."},
  url: {label:"Paste the website link", placeholder:"https://example.com"},
  phone: {label:"Enter an Indian mobile number", placeholder:"98765 43210"}
};
document.querySelectorAll(".tab").forEach(button => button.addEventListener("click", () => {
  document.querySelectorAll(".tab").forEach(tab => tab.classList.remove("active"));
  button.classList.add("active"); activeType = button.dataset.type;
  label.textContent = config[activeType].label; input.placeholder = config[activeType].placeholder; input.value = ""; clearResult();
}));
function clearResult(){resultBox.replaceChildren();resultBox.classList.add("hidden");errorBox.textContent="";}
function addText(tag,text,parent){const element=document.createElement(tag);element.textContent=text;parent.appendChild(element);return element;}
function renderResult(data){
  resultBox.replaceChildren();resultBox.classList.remove("hidden");
  if(data.riskLevel){
    addText("p","RISK LEVEL: "+data.riskLevel,resultBox).className="risk";
    addText("h3",data.riskLevel==="UNKNOWN"?"No conclusion available":"Assessment complete",resultBox);
    if(data.score!==null&&data.score!==undefined)addText("p","Prototype score: "+data.score+"/100 (not a probability)",resultBox);
    const list=document.createElement("ul");
    (data.findings||[]).forEach(item=>addText("li",item.explanation,list));
    (data.recommendations||[]).forEach(item=>addText("li",item,list));
    (data.limitations||[]).forEach(item=>addText("li",item,list));
    resultBox.appendChild(list);
  }else{addText("h3","Phone number format checked",resultBox);addText("p",data.normalisedNumber,resultBox);addText("p",data.message,resultBox);}
}
analyseButton.addEventListener("click",async()=>{
  clearResult();const value=input.value.trim();if(!value){errorBox.textContent="Please enter something to check.";return;}
  if(activeType==="sms"&&/\b\d{4,8}\b/.test(value)&&/otp|one.time.password/i.test(value)){errorBox.textContent="Remove OTP digits before submitting the message.";return;}
  analyseButton.disabled=true;analyseButton.textContent="Checking…";
  try{
    const path=activeType==="sms"?"/analysis/sms":activeType==="url"?"/analysis/url":"/analysis/phone";
    const body=activeType==="sms"?{text:value}:activeType==="url"?{url:value}:{phone:value,countryCode:"+91"};
    const response=await fetch(API_BASE+path,{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(body)});
    const data=await response.json();if(!response.ok)throw new Error(data.message||"The API rejected this request.");renderResult(data);
  }catch(error){errorBox.textContent=error.message.includes("Failed to fetch")?"Cannot reach API. Start backend on localhost:8080 and open web client through a local server.":error.message;}
  finally{analyseButton.disabled=false;analyseButton.textContent="Analyse safely";}
});
fetch(API_BASE+"/health").then(r=>{document.querySelector("#api-status").textContent=r.ok?"API connected":"API not ready";}).catch(()=>{document.querySelector("#api-status").textContent="Start backend to connect";});
