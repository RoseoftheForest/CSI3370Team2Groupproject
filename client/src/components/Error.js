import { useAuth0 } from "@auth0/auth0-react";

const Error = () => {
    const { error } = useAuth0();

    return (
        <div className="bg-dark p-3 text-center">
            Oops... {error.message}
        </div>
    );
}

export default Error;