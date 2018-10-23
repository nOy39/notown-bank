<template>
    <div>
        <b-navbar toggleable="md" type="dark" variant="secondary">

            <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>

            <b-navbar-brand href="#">Pac-Man Bank</b-navbar-brand>

            <b-collapse is-nav id="nav_collapse">

                <!--<b-navbar-nav>-->
                <!--<b-nav-item href="/">Link</b-nav-item>-->
                <!--<b-nav-item href="#" disabled>Disabled</b-nav-item>-->
                <!--</b-navbar-nav>-->

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">

                    <b-nav-form>
                        <b-form-input size="sm" class="mr-sm-2" type="text" placeholder="Search"/>
                        <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
                    </b-nav-form>


                    <b-nav-item-dropdown right v-if="auth">
                        <!-- Using button-content slot -->
                        <template slot="button-content">
                            <em>{{user.username}}</em>
                        </template>
                        <b-dropdown-item href="#">Profile</b-dropdown-item>
                        <b-dropdown-item href="#">Signout</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <template v-if="auth">
                        <b-nav-item-dropdown text="Accounts" right v-if="client">
                            <b-dropdown-item href="#">EN</b-dropdown-item>
                            <b-dropdown-item href="#">ES</b-dropdown-item>
                            <b-dropdown-item href="#">RU</b-dropdown-item>
                            <b-dropdown-item href="#">FA</b-dropdown-item>
                        </b-nav-item-dropdown>
                        <b-nav-item-dropdown text="Message" right>
                            <b-dropdown-item href="#" disabled>Unread</b-dropdown-item>
                            <b-dropdown-item href="#">Incoming</b-dropdown-item>
                            <b-dropdown-item href="#">Outgoing</b-dropdown-item>
                            <b-dropdown-item href="#" disabled>Trash</b-dropdown-item>
                        </b-nav-item-dropdown>
                        <b-button size="sm" class="my-2 my-sm-0" type="submit" @click="logOut">Log out</b-button>
                    </template>
                    <template v-else>
                        <b-button size="sm" class="my-2 my-sm-0" type="submit" @click="logOut">Log in</b-button>
                        <b-btn v-b-modal.modal-center>Sign in</b-btn>
                    </template>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <div>


            <!-- Modal Component -->
            <b-modal id="modal-center"
                     ref="modal"
                     title="Authorize... "
                     @ok="logIn"
                     @shown="clear">
                <form @submit.stop.prevent="logIn">
                    <b-form-input type="text"
                                  placeholder="Username or email"
                                  v-model="usernameOrEmail"></b-form-input>
                    <b-form-input type="password"
                                  placeholder="Password"
                                  v-model="password" my-3></b-form-input>
                </form>
            </b-modal>
        </div>
    </div>
</template>

<script>

    import {AXIOS} from "../../http-common";

    export default {
        name: "Navbar",
        data() {
            return {
                usernameOrEmail: '',
                password: ''
            }
        },
        computed: {
            user() {
                return this.$store.getters.getUser
            },
            auth() {
                return this.$store.getters.getAuth
            },
            client() {
                return this.$store.getters.getClient
            },
            manager() {
                return this.$store.getters.getManager
            }
        },
        methods: {
            logOut() {
                localStorage.clear()
                this.$store.dispatch('clearAuth')
                this.$router.push('/')
            },
            logIn() {
                this.$store.dispatch('isLoading', true)
                AXIOS({
                    method: 'post',
                    url: '/auth/signin',
                    data: {
                        usernameOrEmail: this.usernameOrEmail,
                        password: this.password
                    }
                })
                    .then(response => {
                        this.$store.dispatch('setAuth', response)
                        localStorage.setItem('auth', 'Bearer ' + response.data.accessToken)
                        this.$router.push('/account')
                    })
                    .catch(e => {
                        this.$store.dispatch('setError', e.message)
                    })
                    .then(() => {
                        this.$store.dispatch('isLoading', false)
                    })
                    .then(() => {
                        this.roleDispatch()
                    })
            },
            clear() {
                this.usernameOrEmail = ''
                this.password = ''
            }
        }
    }
</script>

<style scoped>

</style>
