<template>
  <div class="create" ref="create">
    <top-tip ref="topTip">
      <div class="tip-title">
        <i class="icon-ok" v-show="tip=='翻唱任务添加成功！'"></i>
        <span class="text">{{tip}}</span>
      </div>
    </top-tip>
    <scroll ref="scroll" class="create-content" :data="discList">
      <form>
        <div class="input-list">
          <input-box ref="inputTitle" placeholder="请输入歌名" @query="onTitleChange"></input-box>
        </div>
        <div class="input-list">
          <input-box ref="inputArtist" placeholder="请输入原唱歌手" @query="onArtistChange"></input-box>
        </div>
        <h1 class="list-title">翻唱AI:</h1>
        <div class="input-list">
          <switches :switches="switches" :currentIndex="currentIndex"
                  @switch="switchItem">
          </switches>
        </div>
        <div class="input-list">
          <div class='container'>
	          <div class="dropzone">
		          <label for="files" class="dropzone-container">
                <div class="file-icon">+</div>
                <div class="dropzone-title">
                  拖拽或 <span class='browse'>浏览</span> 上传mp3文件
                </div>
		          </label>
		          <input id="files" ref="fileInput" name="file" type="file" class="file-input" />
	          </div>
          </div>
          <div class="list-operate">
            <div class="add" @click.once="add" :key="buttonKey">
              <i class="icon-add"></i>
              <span class="text">添加AI翻唱任务</span>
            </div>
          </div>
        </div>
      </form>
    </scroll>
    <router-view></router-view>
    <div class="overlay overlay-effect" v-show="progress!=100">
      <h1>歌曲上传中，请耐心等待...</h1>
      <div class="shell">
        <div class="bar" :style="{ width: progress + '%' }">
          <span>{{ progress }}%</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { playlistMixin } from '../../common/js/mixin'
import { mapActions } from 'vuex'
import Scroll from '../../base/scroll/scroll'
import { postCreate } from '../../api/create'
import InputBox from '../../base/input-box/input-box'
import Switches from '../../base/switches/switches'
import TopTip from '../../base/top-tip/top-tip'
import router from '../../router'

export default {
  mixins: [playlistMixin],
  data() {
    return {
      progress: 100,
      title: '',
      artist: '',
      tip: '翻唱任务添加成功！',
      creates: [],
      discList: [],
      currentIndex: 0,
      switches: [
        {name: '孙燕姿'},
        {name: '周杰伦'},
        {name: '张学友'}
      ],
      buttonKey: 1
    }
  },
  created() {

  },
  methods: {
    onTitleChange(query) {
      this.title = query
    },
    onArtistChange(query) {
      this.artist = query
    },
    validate() {
      if (!this.title) {
        this.tip = '请输入标题！'
        this.showTip()
        this.buttonKey++
        this.progress = 100
        return false
      }
      if (!this.artist) {
        this.tip = '请输入原唱歌手！'
        this.showTip()
        this.buttonKey++
        this.progress = 100
        return false
      }
      if (![0, 1, 2].includes(this.currentIndex)) {
        this.tip = '请选择翻唱AI！'
        this.showTip()
        this.buttonKey++
        this.progress = 100
        return false
      }
      if (document.forms && document.forms[0] && document.forms[0].files && document.forms[0].files.files && document.forms[0].files.files[0] && document.forms[0].files.files[0].type == 'audio/mpeg') {

      } else {
        this.tip = '请上传mp3文件！'
        this.showTip()
        this.buttonKey++
        this.progress = 100
        return false
      }
      this.tip = '翻唱任务添加成功！'
      return true
    },
    showTip() {
      this.$refs.topTip.show()
    },
    add() {
      const onUploadProgress = (progressEvent) => {
        this.progress = Math.round(progressEvent.loaded / progressEvent.total * 100)
      }
      if (this.validate()) {
        this.progress = 0
        postCreate(document.forms[0].files.files[0], this.title, this.artist, this.switches[this.currentIndex].name, onUploadProgress).then((res) => {
          if (res && res.data && res.data.id) {
            this.tip = '翻唱任务添加成功！'
            this.showTip()
            setTimeout(() => {
              this.buttonKey++
              this.progress = 100
              router.push({ path: '/pending' })
            }, 3000)
          } else {
            this.tip = res.message
            this.showTip()
            this.buttonKey++
            this.progress = 100
          }
        })
      }
    },
    switchItem(index) {
      this.currentIndex = index
    },
    handlePlaylist(playlist) {
      const bottom = playlist.length > 0 ? '60px' : ''
      this.$refs.create.style.bottom = bottom
      this.$refs.scroll.refresh()
    },
    ...mapActions([
      
    ])
  },
  components: {
    InputBox,
    Switches,
    Scroll,
    TopTip,
  }
}
</script>

<style lang="stylus" scoped>
  @import "~common/stylus/variable"

  .overlay
    position:fixed
    width:100%
    height:100%
    top: 0
    left: 0
    background: rgba(0, 0, 0, 0.89)
    display: flex
    flex-direction: column 
    justify-content: center
    align-items: center
    .shell
      margin-top: 10px
      height: 20px
      width: 250px
      border: 1px solid #aaa
      border-radius: 13px
      padding: 3px
      .bar
        background: linear-gradient(to right, #B993D6, #8CA6DB)
        height: 20px
        width: 15px
        border-radius: 9px
        span
          float: right
          padding: 4px 5px
          color: #fff
          font-size: 0.7em

  .create
    position: fixed
    width: 100%
    top: 88px
    bottom: 0

    .tip-title
      text-align: center
      padding: 18px 0
      font-size: 0
      .icon-ok
        font-size: $font-size-medium
        color: $color-theme
        margin-right: 4px
      .text
        font-size: $font-size-medium
        color: $color-text

    .create-content
      height: 100%
      margin-top: 20px
      overflow: hidden
      .slider-wrapper
        position: relative
        width: 100%
        overflow: hidden
      .input-list
        width: 80%
        margin: auto
        
        .list-operate
          width: 140px
          margin: 20px auto 30px auto
          .add
            display: flex
            align-items: center
            padding: 8px 16px
            border: 1px solid $color-text-l
            border-radius: 100px
            color: $color-text-l
            .icon-add
              margin-right: 5px
              font-size: $font-size-small-s
            .text
              font-size: $font-size-small


        .container
          box-sizing: border-box
          background-color: #f7f7f7
          width: 100%
          margin: 20px auto
          background: #ffffff
          padding: 1rem
          border-radius: 0.5rem
          box-shadow: 0 0.75rem 1.5rem rgba(#131313, 0.03)
          .dropzone
            border: 0.0625rem solid #c6ccd6
            border-radius: 0.5rem
            background-color: #f9f9fb
            display: block
            .dropzone-container
              padding: 2rem 0
              width: 100%
              height: 100%
              position: relative;
              display: flex
              flex-direction: column
              justify-content: center
              align-items: center
              color: #8c96a8
              z-index: 20
              .dropzone-title
                padding-top: 1.5rem
              .browse
                text-decoration: underline
                color: #007bff
            .file-input
              position: absolute
              width: 100%
              height: 100%
              top: 0
              left: 0
              opacity: 0
              visibility: hidden
              cursor: pointer

      .list-title
        height: 45px
        line-height: 45px
        text-align: center
        font-size: $font-size-medium
        color: $color-theme
</style>
