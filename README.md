## Steps to implement SSL COMMERZ SDK
### 1. Test/Live Account Creation
#### Test/Sandbox
For registration in Sandbox, click the link https://developer.sslcommerz.com/registration/
#### Live/Production
For registration in Production, click the link https://signup.sslcommerz.com/register
### 2. SSLCommerz integration in Android using Test/Sandbox Environment

#### Step 1: Add below dependency to build.gradle (app module) :
    implementation 'com.android.support:support-compat:28.0.0'
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:support-v4:28.0.0'
    implementation 'com.android.support:design:28.0.0'
    implementation 'com. google. code. gson: gson:2.8.5'

#### If project supports androidx artifacts, then please change dependency according to androidx.
    implementation 'androidx.core:core:1.0.0'
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'com.google.code.gson:gson:2.8.5'

#### Step 2: To insert the dependency mentioned in Step 1, follow the steps mentioned below:

From top left corner of project pane,
“Select” Project -> app -> libs -> “Paste” sslCommerzSdk.aar in libs folder

“Select” Project app -> build.gradle (app module) -> add the code below, and sync project:

    repositories {
        flatDir {
         dirs 'libs'
        }
     }
    implementation(name: 'sslCommerzSdk', ext: 'aar')

#### Step 3 :

After successfully completing SDK integration, you can request for transaction.  In order to commence transaction, you need to do the following steps

#### SSLCommerzInitialization (Mandatory to initiate SSLCommerz):

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization(
                "store_id", "store_password",
                amount, CurrencyType.BDT, "transactionID" + randomPrefix,
                "Payment", SdkType.TESTBOX);


#### Afterwards, call the following class to connect with SSLCommerz:

        IntegrateSSLCommerz
                .getInstance(view.getContext())
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .addAdditionalInitializer(additionalInitializer)
                .buildApiCall(this);

##### Thank You.
