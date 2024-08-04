export default function LabelSection({className, id, label, isRequire = false, asChild = false, children, ...props}){
    const wrapperClassName = `flex flex-col gap-2 items-center w-full ${className}`;
    return (
        <div
        className={wrapperClassName}>
            <div className="flex justify-start items-center w-full gap-x-1">
                <span className="font-medium">
                    {label}
                </span>
                {
                    isRequire ?
                    <span className="text-rose-600">*</span>
                    : <></>
                }
            </div>
            {asChild ? children : <></>}
        </div>
    );
}