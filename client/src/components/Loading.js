const Loading = () => {

    return (
        <div className="bg-dark p-3 text-center">
            <Spinner
                color="light"
                size="sm"
            >
                Loading...
            </Spinner>
            <span>
                {' '}Loading...
            </span>
        </div>
    );
}

export default Loading;