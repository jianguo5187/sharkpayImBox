<template>
	<el-container class="chat-page">
		<el-aside width="260px" class="chat-list-box">
			<div class="chat-list-header">
				<el-input width="200px" placeholder="搜索" v-model="searchText">
					<el-button slot="append" icon="el-icon-search"></el-button>
				</el-input>
			</div>
			<div class="chat-list-loadding"  v-if="loading" v-loading="true" element-loading-text="消息接收中..."
				element-loading-spinner="el-icon-loading" element-loading-background="#eee">
				<div class="chat-loading-box"></div>
			</div>
			<el-scrollbar class="chat-list-items">
				<div v-for="(chat,index) in chatStore.chats" :key="index">
					<chat-item v-show="chat.showName.startsWith(searchText)" :chat="chat" :index="index"
						@click.native="onActiveItem(index)" @delete="onDelItem(index)" @top="onTop(index)"
						:active="chat === chatStore.activeChat"></chat-item>
				</div>
			</el-scrollbar>
		</el-aside>
		<el-container class="chat-box">
			<chat-box v-if="chatStore.activeChat" :chat="chatStore.activeChat"></chat-box>
		</el-container>
	</el-container>
</template>

<script>
	import ChatItem from "../components/chat/ChatItem.vue";
	import ChatBox from "../components/chat/ChatBox.vue";

	export default {
		name: "chat",
		components: {
			ChatItem,
			ChatBox
		},
		data() {
			return {
				searchText: "",
				messageContent: "",
				group: {},
				groupMembers: [],
        loginUserId:this.$store.state.userStore.userInfo.id
			}
		},
		methods: {
			onActiveItem(index) {
				this.$store.commit("activeChat", index);
			},
			onDelItem(index) {
				this.$store.commit("removeChat", index);
			},
			onTop(chatIdx) {
				this.$store.commit("moveTop", chatIdx);
			},
      setFriendRemark() {
        console.log("setFriendRemark");
        let friends = this.$store.state.friendStore.friends;
        for(var i=0;i<friends.length;i++){
          var objFriend = friends[i];
          if(objFriend.remarkName != undefined && objFriend.remarkName != ""){
            this.setChatShowName(objFriend.id,objFriend.remarkName,objFriend.userIpAddress,objFriend.thirdUserId);
          }else {
            this.setChatShowName(objFriend.id,objFriend.nickName,objFriend.userIpAddress,objFriend.thirdUserId);
          }
        }
      },
      setChatShowName(friednId,nickName,updateChatIpAddress,thirdUserId){
        let chats = this.$store.state.chatStore.chats;
        for(var j=0;j<chats.length;j++){
          var objChat = chats[j];
          if(objChat.targetId == friednId){
            console.log("");
            this.$store.commit("activeChatIndex", j);
            this.$store.commit("updateChatRemark", nickName);
            this.$store.commit("updateChatIpAddress", updateChatIpAddress);
            this.$store.commit("updateChatThirdUserId", thirdUserId);
            break;
          }
        }
      }
    },
    mounted() {
      setTimeout(() => {
        this.setFriendRemark();
        if(this.chatStore.chats.length == 0){
          this.$router.push("/home/friend");
        }
      }, 1000);
    },
		computed: {
			chatStore() {
        console.log(1);
				return this.$store.state.chatStore;
			},
			loading(){
        console.log(2);
				return this.chatStore.loadingGroupMsg || this.chatStore.loadingPrivateMsg
			}
		}
	}
</script>

<style lang="scss">
	.chat-page {
		.chat-list-box {
			display: flex;
			flex-direction: column;
			border: #dddddd solid 1px;
			background: white;
			width: 3rem;

			.chat-list-header {
				padding: 5px;
				background-color: white;
				line-height: 50px;
			}
			
			.chat-list-loadding{
				height: 50px;
				background-color: #eee;
				
				.chat-loading-box{
					height: 100%;
				}
			}
			
			.chat-list-items {
				flex: 1;
			}
		}
	}
</style>