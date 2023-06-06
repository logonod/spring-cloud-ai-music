<template>
  <div class="tab">
    <router-link tag="div" class="tab-item" to="/other">
      <span class="tab-link">随便听听</span>
    </router-link>
    <router-link tag="div" class="tab-item" to="/finished">
      <span class="tab-link">已完成</span>
    </router-link>
    <router-link tag="div" class="tab-item" to="/pending">
      <span class="tab-link">待完成</span>
    </router-link>
    <router-link tag="div" class="tab-item" to="/create">
      <span class="tab-link">创建翻唱</span>
    </router-link>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="stylus" scoped>
  @import "~common/stylus/variable"
  .tab
    display: flex
    height: 44px
    line-height: 44px
    font-size: $font-size-medium
    .tab-item
      flex: 1
      text-align: center
      .tab-link
        padding-bottom: 5px
        color: $color-text-l
      &.router-link-active
        .tab-link
          color: $color-theme
          border-bottom: 2px solid $color-theme
</style>
