
<template>
  <router-view>
  </router-view>

</template>
<script>
import router from './router';

export default {
  name: "App",
  data() {
    return {
      flag: true
    };
  },
  components: {router}
}

</script>


