# Obtaining and Safeguarding API Keys

To successfully build the "CookAI" application and fetch images, API keys from [serpGoogle](https://serpapi.com) and the OpenAI platform are required. It's crucial to store these keys securely within the `local.properties` file. Follow the steps below to obtain and safeguard your API keys:

## 1. Obtain API Keys

- **SerpGoogle API Key:**
  1. Visit [serpapi.com](https://serpapi.com) and sign up for an account.
  2. Once registered, obtain your API key from your account dashboard.

- **OpenAI API Key:**
  1. Visit the OpenAI platform and sign in or create an account.
  2. Navigate to the API section and generate a new API key.

## 2. Store API Keys in local.properties

For security reasons, it's important to store your API keys in a separate `local.properties` file. This file should not be included in version control to prevent exposure of sensitive information. Here's a step-by-step guide:

1. Open the project in Android Studio.

2. Create a `local.properties` file in the root directory of your project.

3. Inside `local.properties`, add the following lines:

    ```properties
    serpApiKey="YOUR_SERPAPI_KEY"
    openaiApiKey="YOUR_OPENAI_KEY"
    ```

    Replace `YOUR_SERPAPI_KEY` and `YOUR_OPENAI_KEY` with the respective API keys you obtained.

## 3. Secure Your Keys with GitHub Secrets

To ensure the security of your API keys, avoid committing `local.properties` to version control. Instead, use GitHub Secrets to securely store and access these keys during workflows. Refer to [this tutorial](https://jordan-mungujakisa.medium.com/how-to-safeguard-your-api-keys-in-android-projects-with-github-secrets-5679e0e89a77) for detailed steps on setting up GitHub Secrets.

## Building the Application

Now that your API keys are safely stored, you can proceed to build the "CookAI" application with the assurance that sensitive information is protected. If you encounter any issues or have further questions, feel free to reach out for assistance. Happy coding!
