webpackJsonp([1],{"4SJn":function(t,e){},"6WfN":function(t,e){},NwRc:function(t,e){},T90t:function(t,e){t.exports="object"==typeof self?self.FormData:window.FormData},mFje:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=s("Dd8w"),n=s.n(i),r=s("F4+m"),a=s("NYxO"),o=s("qwAB"),c=s("Xxa5"),l=s.n(c),u=s("mvHQ"),p=s.n(u),d=s("exGp"),h=s.n(d),f=s("mtWM"),v=s.n(f),m=s("T90t"),w=s.n(m);function y(t){var e=t.split(".")[1].replace(/-/g,"+").replace(/_/g,"/"),s=decodeURIComponent(window.atob(e).split("").map(function(t){return"%"+("00"+t.charCodeAt(0).toString(16)).slice(-2)}).join(""));return JSON.parse(s)}var _,g=(_=h()(l.a.mark(function t(e,s,i,n,r){var a,o,c,u,d;return l.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(!(a=localStorage.getItem("accessToken"))){t.next=13;break}return o=y(a),c=o.id,(u=new w.a).append("file",e),u.append("info",new Blob([p()({user:c,title:s,artist:i,ai:n})],{type:"application/json"})),t.next=9,v()({method:"post",url:"/api/music/add",data:u,headers:{"Content-Type":"multipart/form-data",Authorization:"Bearer "+a},onUploadProgress:r}).then(function(t){return t}).catch(function(t){return t});case 9:return d=t.sent,t.abrupt("return",d);case 13:window.location.href="/signin";case 14:return t.abrupt("return",[]);case 15:case"end":return t.stop()}},t,this)})),function(t,e,s,i,n){return _.apply(this,arguments)}),C=s("hhm8"),x={data:function(){return{query:""}},props:{placeholder:{type:String,default:"请输入歌名"}},created:function(){var t=this;this.$watch("query",Object(C.a)(function(e){t.$emit("query",e)},200))},methods:{clear:function(){this.query=""},setQuery:function(t){this.query=t},blur:function(){this.$refs.query.blur()}}},b={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"input-box"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.query,expression:"query"}],ref:"query",staticClass:"box",attrs:{type:"text",placeholder:t.placeholder},domProps:{value:t.query},on:{input:function(e){e.target.composing||(t.query=e.target.value)}}}),t._v(" "),s("i",{directives:[{name:"show",rawName:"v-show",value:t.query,expression:"query"}],staticClass:"icon-dismiss",on:{click:t.clear}})])},staticRenderFns:[]};var I=s("VU/8")(x,b,!1,function(t){s("pJmI")},"data-v-9dc1aa1a",null).exports,T={props:{switches:{type:Array,default:function(){return[]}},currentIndex:{type:Number,default:0}},methods:{switchItem:function(t){this.$emit("switch",t)}}},q={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("ul",{staticClass:"switches"},t._l(t.switches,function(e,i){return s("li",{key:i,staticClass:"switch-item",class:{active:t.currentIndex===i},on:{click:function(e){return t.switchItem(i)}}},[s("span",[t._v(t._s(e.name))])])}),0)},staticRenderFns:[]};var N=s("VU/8")(T,q,!1,function(t){s("6WfN")},"data-v-2742d29e",null).exports,F={data:function(){return{showFlag:!1}},props:{delay:{type:Number,default:2e3}},methods:{show:function(){var t=this;this.showFlag=!0,clearTimeout(this.timer),this.timer=setTimeout(function(){t.hide()},this.delay)},hide:function(){this.showFlag=!1}}},k={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("transition",{attrs:{name:"drop"}},[s("div",{directives:[{name:"show",rawName:"v-show",value:t.showFlag,expression:"showFlag"}],staticClass:"top-tip",on:{click:function(e){return e.stopPropagation(),t.hide.apply(null,arguments)}}},[t._t("default")],2)])},staticRenderFns:[]};var S=s("VU/8")(F,k,!1,function(t){s("4SJn")},"data-v-0f4ed943",null).exports,$=s("YaEn"),A={mixins:[r.b],data:function(){return{progress:100,title:"",artist:"",tip:"翻唱任务添加成功！",creates:[],discList:[],currentIndex:0,switches:[{name:"孙燕姿"},{name:"周杰伦"},{name:"张学友"}],buttonKey:1}},created:function(){},methods:n()({onTitleChange:function(t){this.title=t},onArtistChange:function(t){this.artist=t},validate:function(){return this.title?this.artist?[0,1,2].includes(this.currentIndex)?document.forms&&document.forms[0]&&document.forms[0].files&&document.forms[0].files.files&&document.forms[0].files.files[0]&&"audio/mpeg"==document.forms[0].files.files[0].type?(this.tip="翻唱任务添加成功！",!0):(this.tip="请上传mp3文件！",this.showTip(),this.buttonKey++,this.progress=100,!1):(this.tip="请选择翻唱AI！",this.showTip(),this.buttonKey++,this.progress=100,!1):(this.tip="请输入原唱歌手！",this.showTip(),this.buttonKey++,this.progress=100,!1):(this.tip="请输入标题！",this.showTip(),this.buttonKey++,this.progress=100,!1)},showTip:function(){this.$refs.topTip.show()},add:function(){var t=this;this.validate()&&(this.progress=0,g(document.forms[0].files.files[0],this.title,this.artist,this.switches[this.currentIndex].name,function(e){t.progress=Math.round(e.loaded/e.total*100)}).then(function(e){e&&e.data&&e.data.id?(t.tip="翻唱任务添加成功！",t.showTip(),setTimeout(function(){t.buttonKey++,t.progress=100,$.a.push({path:"/pending"})},3e3)):(t.tip=e.message,t.showTip(),t.buttonKey++,t.progress=100)}))},switchItem:function(t){this.currentIndex=t},handlePlaylist:function(t){var e=t.length>0?"60px":"";this.$refs.create.style.bottom=e,this.$refs.scroll.refresh()}},Object(a.b)([])),components:{InputBox:I,Switches:N,Scroll:o.a,TopTip:S}},K={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{ref:"create",staticClass:"create"},[s("top-tip",{ref:"topTip"},[s("div",{staticClass:"tip-title"},[s("i",{directives:[{name:"show",rawName:"v-show",value:"翻唱任务添加成功！"==t.tip,expression:"tip=='翻唱任务添加成功！'"}],staticClass:"icon-ok"}),t._v(" "),s("span",{staticClass:"text"},[t._v(t._s(t.tip))])])]),t._v(" "),s("scroll",{ref:"scroll",staticClass:"create-content",attrs:{data:t.discList}},[s("form",[s("div",{staticClass:"input-list"},[s("input-box",{ref:"inputTitle",attrs:{placeholder:"请输入歌名"},on:{query:t.onTitleChange}})],1),t._v(" "),s("div",{staticClass:"input-list"},[s("input-box",{ref:"inputArtist",attrs:{placeholder:"请输入原唱歌手"},on:{query:t.onArtistChange}})],1),t._v(" "),s("h1",{staticClass:"list-title"},[t._v("翻唱AI:")]),t._v(" "),s("div",{staticClass:"input-list"},[s("switches",{attrs:{switches:t.switches,currentIndex:t.currentIndex},on:{switch:t.switchItem}})],1),t._v(" "),s("div",{staticClass:"input-list"},[s("div",{staticClass:"container"},[s("div",{staticClass:"dropzone"},[s("label",{staticClass:"dropzone-container",attrs:{for:"files"}},[s("div",{staticClass:"file-icon"},[t._v("+")]),t._v(" "),s("div",{staticClass:"dropzone-title"},[t._v("\n                  拖拽或 "),s("span",{staticClass:"browse"},[t._v("浏览")]),t._v(" 上传mp3文件\n                ")])]),t._v(" "),s("input",{ref:"fileInput",staticClass:"file-input",attrs:{id:"files",name:"file",type:"file"}})])]),t._v(" "),s("div",{staticClass:"list-operate"},[s("div",{key:t.buttonKey,staticClass:"add",on:{"~click":function(e){return t.add.apply(null,arguments)}}},[s("i",{staticClass:"icon-add"}),t._v(" "),s("span",{staticClass:"text"},[t._v("添加AI翻唱任务")])])])])])]),t._v(" "),s("router-view"),t._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:100!=t.progress,expression:"progress!=100"}],staticClass:"overlay overlay-effect"},[s("h1",[t._v("歌曲上传中，请耐心等待...")]),t._v(" "),s("div",{staticClass:"shell"},[s("div",{staticClass:"bar",style:{width:t.progress+"%"}},[s("span",[t._v(t._s(t.progress)+"%")])])])])],1)},staticRenderFns:[]};var J=s("VU/8")(A,K,!1,function(t){s("NwRc")},"data-v-727bf077",null);e.default=J.exports},mvHQ:function(t,e,s){t.exports={default:s("qkKv"),__esModule:!0}},pJmI:function(t,e){},qkKv:function(t,e,s){var i=s("FeBl"),n=i.JSON||(i.JSON={stringify:JSON.stringify});t.exports=function(t){return n.stringify.apply(n,arguments)}}});
//# sourceMappingURL=1.cc8d6ff54c1da22eb017.js.map