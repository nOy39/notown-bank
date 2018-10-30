<template>
    <div class="my-3">
        <div class="account-card">
            <b-card header="Current bank account info"
                    header-tag="header"
                    :footer="'<em>Balance: </em><b>'+accountInfo.sum+'</b>'"
                    footer-tag="footer"
                    :title="'ACCOUNT NUMBER: <em>' + accountInfo.uniqCheckId + '</em>'">
                <b-list-group>
                    <b-list-group-item>
                        <dl class="row">
                            <dt class="col-sm-3">Account type:</dt>
                            <dd class="col-sm-9"><em>{{accountInfo.type}}</em></dd>
                        </dl>
                    </b-list-group-item>
                    <b-list-group-item>
                        <dl class="row">
                            <dt class="col-sm-3">Currency:</dt>
                            <dd class="col-sm-9"><em>{{accountInfo.currency}}</em></dd>
                        </dl>
                    </b-list-group-item>
                    <b-list-group-item>
                        <dl class="row">
                            <dt class="col-sm-3">Last updated:</dt>
                            <dd class="col-sm-9"><em>{{ moment(accountInfo.updateAt).format('LLL') }}</em></dd>
                        </dl>
                    </b-list-group-item>
                </b-list-group>
            </b-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AccountTab",
        props: {
            accountInfo: Object
        },
        data() {
            return {

            }
        }
    }
</script>

<style scoped>

</style>
